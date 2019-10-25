package com.skyedu.service;

import com.skyedu.sdk.wechat.model.JSSignature;

/**
 * 微信JSService
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-22
 */
public abstract interface WeChatJSService {
	
	/**
	 * 获得jsapi_ticket
	 * @return
	 */
	public String getJsapiTicket(String accessToken);
	
	public JSSignature getJSSignature(String targetUrl);

}
