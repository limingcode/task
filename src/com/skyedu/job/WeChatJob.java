package com.skyedu.job;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.skyedu.service.WeChatJSService;
import com.skyedu.service.WeChatService;

/**
 * 微信定时任务
 * @author xj.chen
 * @date 2015-10-17
 */
@Component
public class WeChatJob implements ServletContextAware{
	
	private ServletContext servletContext;

	@Resource private WeChatService weChatService;
	@Resource private WeChatJSService weChatJSService;

	@Scheduled(cron = "0 0 */1 * * ?")
	public void resetAccessToken() {
		/*String accessToken = weChatService.getAccessToken();
		servletContext.setAttribute("access_token", accessToken);
		servletContext.setAttribute("jsapi_ticket", weChatJSService.getJsapiTicket(accessToken));*/
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
