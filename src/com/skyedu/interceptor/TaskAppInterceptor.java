package com.skyedu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TaskAppInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(TaskOaInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String userStudentId = request.getParameter("userStudentId");
		if (!StringUtils.isEmpty(userStudentId)) {
				return true;
		} 
		return false;
	}
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("当前使用接口: "+request.getRequestURI()+"?"+request.getQueryString());
        log.info("请求头: "+request.getHeader("token-id"));
        super.postHandle(request, response, handler, modelAndView);

    }
}
