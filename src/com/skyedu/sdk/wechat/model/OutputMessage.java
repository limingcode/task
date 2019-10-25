package com.skyedu.sdk.wechat.model;

import java.util.Date;

import com.skyedu.sdk.wechat.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信发送被动响应消息的抽象类
 *
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-16
 */
public abstract class OutputMessage implements java.io.Serializable {

	private static final long serialVersionUID = 299899012501846059L;

	/**
     * 接收方帐号（收到的OpenID）
     */
	@XStreamAlias("ToUserName")
	@XStreamCDATA
    private String toUserName;
	
    /**
     * 开发者微信号
     */
	@XStreamAlias("FromUserName")
	@XStreamCDATA
    private String fromUserName;
	
    /**
     * 消息创建时间 （整型）
     */
	@XStreamAlias("CreateTime")
    private Long createTime;
	
    /**
     * 消息类型
     */
	@XStreamAlias("MsgType")
	@XStreamCDATA
    private String msgType;
	
	public OutputMessage() {
		this.createTime = (new Date()).getTime();
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}