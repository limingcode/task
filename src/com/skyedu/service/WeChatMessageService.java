package com.skyedu.service;

import java.util.List;

import com.skyedu.sdk.wechat.model.Item;
import com.skyedu.sdk.wechat.model.Template;

/**
 * 微信消息Service
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-14
 */
public abstract interface WeChatMessageService {
	
	/**
	 * 发送文本消息
	 * @param toUserName 接收方帐号（收到的OpenID）
	 * @param fromUserName 开发者微信号
	 * @param content 消息内容
	 * @return
	 */
	public String sendTextMsg(String toUserName, String fromUserName, String content);
	
	/**
	 * 发送图文消息
	 * @param toUserName 接收方帐号（收到的OpenID）
	 * @param fromUserName 开发者微信号
	 * @param articles 消息内容
	 * @return
	 */
	public String sendImgMsg(String toUserName, String fromUserName, List<Item> articles) ;
	
	/**
	 * 发送模板消息
	 * @param template
	 * @return
	 */
	public boolean sendTemplate(Template template);
	
}
