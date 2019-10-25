package com.skyedu.sdk.wechat.model;

import com.skyedu.sdk.wechat.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 被动响应消息-文本消息
 * 
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-16
 */
@XStreamAlias("xml")
public class TextOutputMessage extends OutputMessage {

	private static final long serialVersionUID = 2028218899332379011L;
	
	/**
	 * 文本消息
	 */
	@XStreamAlias("Content")
	@XStreamCDATA
	private String content;
	
	public TextOutputMessage(String toUserName, String fromUserName,
			String content) {
		super.setToUserName(toUserName);
		super.setFromUserName(fromUserName);
		super.setMsgType("text");
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
}