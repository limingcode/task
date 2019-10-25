package com.skyedu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 前端过滤器
 * @author Administrator
 *
 */
public class TaskInterceptor extends HandlerInterceptorAdapter {
	//@Resource private EmployeeDAO em;
	public static final String LOGIN_STUDENT = "LOGIN_STUDENT";//登陆用户信息
	public static final String SCORE_STUDENT = "SCORE_STUDENT";//登陆用户积分信息


	public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html;charset=UTF-8");/*
		Employee info = (Employee) request.getSession().getAttribute(LOGIN_STUDENT);
		if(info==null||info.getStatus()!=1)
		{
			PrintWriter out = response.getWriter();  
            StringBuilder builder = new StringBuilder();
            if(info==null)
            	builder.append("页面停留时间过长，请在微信中重新打开");  
            out.print(builder.toString());  
            out.close();
            return false;
		}else if(!isAjaxRequest(request)){//不是ajax请求的情况下才进行数据刷新
			//刷新用户数据
		}*/
		return super.preHandle(request, response, handler);
	}

	/**
	 * 判断是否为ajax请求
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
	}
}
