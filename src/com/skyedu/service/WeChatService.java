package com.skyedu.service;

import java.util.Map;

import net.sf.json.JSONObject;

import com.skyedu.sdk.wechat.model.AccessToken;
import com.skyedu.sdk.wechat.model.UserInfo;

/**
 * 微信Service
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-14
 */
public abstract interface WeChatService {
	
	/**
	 * 验证服务器地址的有效性
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce);
	
	/**
	 * 获得全局唯一票据accessToken
	 * @return
	 */
	public String getAccessToken();
	
	/**
	 * 获取网页授权参数
	 * @return
	 */
	public Map<String, String> getAuthorizeParameterMap(String redirectUrl);
	
	/**
	 * 网页授权获得token
	 * @param code
	 * @return
	 */
	public AccessToken accessToken(String code);
	
	/**
	 * 拉取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public UserInfo userinfo(String accessToken, String openid);
	
	/**
	 * 创建二维码
	 * @param actionName二维码类型:QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
	 * @param sceneId 场景值ID
	 * @param sceneStr 场景值ID
	 * @return
	 */
	public JSONObject createTicket(String actionName, String sceneId, String sceneStr);
	
	/**
	 * 创建自定义菜单
	 * @param accessToken
	 */
	public void createMenu(String accessToken);
	
	/**
	 * 多媒体下载
	 * @param mediaId
	 */
	public String mediaDownload(String mediaId);
}
