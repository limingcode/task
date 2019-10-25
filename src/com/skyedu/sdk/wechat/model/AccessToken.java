package com.skyedu.sdk.wechat.model;

import org.json.JSONException;

import net.sf.json.JSONObject;

/**
 * 通过code换取网页授权access_token的bean
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-18
 */
public class AccessToken {

	/**
	 * 网页授权接口调用凭证
	 */
	private String accessToken;
	/**
	 * 接口调用凭证超时时间
	 */
	private String expiresIn;
	/**
	 * 用户刷新access_token
	 */
	private String refreshToken;
	/**
	 * 用户唯一标识
	 */
	private String openid;
	/**
	 * 用户授权的作用域
	 */
	private String scope;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	private String unionid;
	
	public void infoJSONObject(JSONObject jsonObject) throws JSONException{
		accessToken = jsonObject.optString("access_token");
		expiresIn = jsonObject.optString("expires_in");
		refreshToken = jsonObject.optString("refresh_token");
		openid = jsonObject.optString("openid");
		scope = jsonObject.optString("scope");
		unionid = jsonObject.optString("unionid");
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}
