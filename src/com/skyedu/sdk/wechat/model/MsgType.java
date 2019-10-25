package com.skyedu.sdk.wechat.model;

/**
 * 消息类型
 *
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-16
 */
public enum MsgType {

    Text("text"),
    Image("image"),
    Music("music"),
    Video("video"),
    Voice("voice"),
    Location("location"),
    Link("link"),
    Event("event");
    private String msgType = "";

	MsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the msgType
	 */
	@Override
	public String toString() {
		return msgType;
	}
}
