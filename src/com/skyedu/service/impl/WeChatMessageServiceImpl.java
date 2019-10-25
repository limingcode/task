package com.skyedu.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.skyedu.sdk.wechat.WXXStreamHelper;
import com.skyedu.sdk.wechat.model.NewsOutputMessage;
import com.skyedu.sdk.wechat.model.Item;
import com.skyedu.sdk.wechat.model.Template;
import com.skyedu.sdk.wechat.model.TextOutputMessage;
import com.skyedu.service.WeChatMessageService;
import com.thoughtworks.xstream.XStream;
import com.util.HttpClientUtils;

/**
 * 微信Impl
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-14
 */
@Service
public class WeChatMessageServiceImpl implements WeChatMessageService{
	
	private static final Logger LOGGER = Logger.getLogger(WeChatMessageServiceImpl.class);
	
	@Override
	public String sendTextMsg(String toUserName, String fromUserName,
			String content) {
		TextOutputMessage outputMessage = new TextOutputMessage(toUserName, fromUserName, content);
		XStream xStream = WXXStreamHelper.createXstream();
		xStream.processAnnotations(TextOutputMessage.class);
		return xStream.toXML(outputMessage);
	}
	
	@Override
	public String sendImgMsg(String toUserName, String fromUserName,
			List<Item> articles) {
		NewsOutputMessage outputMessage = new NewsOutputMessage(toUserName, fromUserName, articles);
		XStream xStream = WXXStreamHelper.createXstream();
		xStream.processAnnotations(NewsOutputMessage.class);
		return xStream.toXML(outputMessage);
	}

	@Override
	public boolean sendTemplate(Template template) {
		JSONObject json = null;
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        ServletContext servletContext = webApplicationContext.getServletContext();
		String accessToken = servletContext.getAttribute("access_token").toString();
		String param = JSONObject.fromObject(template).toString();
		try {
			String data = HttpClientUtils.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, param);
			json = JSONObject.fromObject(new String(data.getBytes("ISO8859-1"), "UTF-8"));
			if (json.getInt("errcode") == 0) {
				return true;
			}
			System.out.println("发送模板消息失败："+json.toString());
			LOGGER.error("发送模板消息失败："+json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
