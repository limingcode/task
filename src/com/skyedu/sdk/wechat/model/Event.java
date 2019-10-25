package com.skyedu.sdk.wechat.model;

/**
 * 事件类型
 * 
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-16
 */
public enum Event {

	/** 订阅 */
	Subscribe("subscribe"),
	/** 取消订阅 */
	Unsubscribe("unsubscribe"),
	/** 扫码-已关注 */
	Scan("SCAN");
	
    private String event = "";

	Event(String event) {
		this.event = event;
	}

	/**
	 * @return the event
	 */
	@Override
	public String toString() {
		return event;
	}
}
