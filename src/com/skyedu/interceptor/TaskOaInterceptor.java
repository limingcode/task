package com.skyedu.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skyedu.service.RoleService;
import com.util.SystemConfigures;

public class TaskOaInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private RoleService roleService;
    private static Logger log = Logger.getLogger(TaskOaInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
			HttpSession session = request.getSession();
			String oaId = request.getParameter("oaId");
			if (StringUtils.isEmpty(oaId)) {
				Object attribute = session.getAttribute("oaId");
				if (attribute != null) {
					if (request.getServletPath().equals("/work/removeWork.jhtml")) {
						String[] courses = request.getParameterValues("courses");
						String[] ceshi = new String[]{"7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"
								,"26","27","28","29","30","31","32","33","34","35","36","37","38","39","41","42","43","44"};
						List<String> asList = Arrays.asList(ceshi);
						if (courses!=null && courses.length==1 && asList.contains(courses[0])) {
							return true;
						}
					}
					if (!userRole(request)) {
						PrintWriter out = response.getWriter();
						out.print("<script>alert('您没有相应权限浏览或操作，请联系管理员开通！');window.history.back(-1);</script>");
						out.flush();
						out.close();
						return false;
					}
					return true;
				} else {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=UTF-8");
					// 获取项目路径
					String path = request.getContextPath();
					String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
							+ path + "/";
					PrintWriter out = response.getWriter();
					out.print("<script>alert('您还没有登录或用户信息已失效，请重新登录！');window.top.location.href='" + basePath
							+ "/index.jsp';</script>");
					out.flush();
					out.close();
					return false;
				}
			} else {
				if (!userRole(request)) {
					PrintWriter out = response.getWriter();
					out.print("<script>alert('您没有相应权限浏览或操作，请联系管理员开通！');window.history.back(-1);</script>");
					out.flush();
					out.close();
					return false;
				}
				session.setAttribute("oaId", Integer.valueOf(oaId));
				return true;
			}
		
	}

	private boolean userRole(HttpServletRequest request){
		String requestPath = request.getServletPath();
		if (SystemConfigures.getRoleUrls().contains(requestPath)) {
			Object oaId = request.getSession().getAttribute("oaId");
			List<String> userRoleList = roleService.getUserRoleList((Integer)oaId);
			if (userRoleList!=null && userRoleList.contains(requestPath)) {
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("当前使用接口: "+request.getRequestURI()+"?"+request.getQueryString());
        log.info("请求头: "+request.getHeader("token-id"));
 	       super.postHandle(request, response, handler, modelAndView);

    }
}
