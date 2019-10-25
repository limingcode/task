package com.skyedu.sdk.wechat.model;

/**
 * 签名信息
 * 
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-22
 */
public class JSSignature {
	
	private static final String JS_API = "chooseImage,previewImage,uploadImage,downloadImage";

	/**
	 * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
	 */
	private String signature;
	/**
	 * 时间戳
	 */
	private String timestamp;
	/**
	 * 随机数
	 */
	private String nonceStr;
	/**
	 * 网页地址
	 */
	private String url;
	
	public String[] getJsApiList() {
        return JS_API.split(",");
    }
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "JSSignature [signature=" + signature + ", timestamp="
				+ timestamp + ", nonceStr=" + nonceStr + ", url=" + url + "]";
	}

}
