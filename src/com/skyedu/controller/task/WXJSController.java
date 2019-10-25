/*package com.skyedu.controller.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.interceptor.AdminInterceptor;
import com.skyedu.sdk.wechat.model.JSSignature;
import com.skyedu.service.WeChatJSService;
import com.skyedu.util.StringUtil;

*//**
 * 微信公众号JSController
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-23
 *//*
@Controller("taskWXJSController")
@RequestMapping("/wxJS")
public class WXJSController extends BaseController {
	
	@Resource 
	private WeChatJSService weChatJSService;
	
	@Value("${host.port}")
	private String host_port;
	@Value("${host.href}")
	private String host_href;
	
	
	*//**
	 * 获得签名信息
	 * @param url
	 * @param request
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/getJSSignature", method = RequestMethod.GET)
	@ResponseBody
	public JSSignature getJSSignature(String url,HttpServletRequest request, ModelMap model) {
//	    Pattern pattern = Pattern.compile(":[0-9]{2,5}/");
//		Matcher matcher = pattern.matcher(url);
//		url = matcher.replaceAll("/");
//		String href = request.getRequestURL().toString();
//		String hrefHade = href.replaceAll(request.getRequestURI(), "").replaceAll(":"+host_port, "");
//		url = url.replaceAll(hrefHade, host_href+(!StringUtil.isEmpty(host_port)?":"+host_port:""));
		return weChatJSService.getJSSignature(url);
	}
	
}
*/