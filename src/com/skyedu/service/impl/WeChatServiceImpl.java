package com.skyedu.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.skyedu.sdk.wechat.SHA1;
import com.skyedu.sdk.wechat.model.AccessToken;
import com.skyedu.sdk.wechat.model.UserInfo;
import com.skyedu.service.FileService;
import com.skyedu.service.FileService.FileType;
import com.skyedu.service.WeChatService;
import com.util.HttpClientUtils;

/**
 * 微信Impl
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-14
 */
@Service
public class WeChatServiceImpl implements WeChatService{
	
	@Value("${wechat.appid}")
	private String appid;

	@Value("${wechat.appsecret}")
	private String appsecret;

	@Value("${wechat.token}")
	private String token;

	@Value("${wechat.redirect_uri}")
	private String redirectUri;
	
	@Value("${wechat.menu}")
	private String menu;
	
	@Value("${setting.siteUrl}")
	private String siteUrl;
	
	private static final Logger LOGGER = Logger.getLogger(WeChatServiceImpl.class);

	@Resource 
	private FileService fileService;
	
	@Override
	public boolean checkSignature(String signature, String timestamp,
			String nonce) {
		List<String> params = new ArrayList<String>();
		params.add(token);
		params.add(timestamp);
		params.add(nonce);
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
		if (temp.equals(signature)) {
			return true;
		}
		return false;
	}
	
	public String getAccessToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appid + "&secret=" + appsecret;
		String data = HttpClientUtils.get(url);
		JSONObject json = JSONObject.fromObject(data);
		if (json.containsKey("errcode")) {
			return null;
		} else {
			return json.getString("access_token");
		}
	}

	@Override
	public Map<String, String> getAuthorizeParameterMap(String redirectUrl) {
		String url = redirectUri;
		if (redirectUrl != null) {
			url += "?redirectUrl=" + redirectUrl;
			url = url.replaceAll("\\&", "%26");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", appid);
		map.put("redirect_uri", url);
		map.put("response_type", "code");
		map.put("scope", "snsapi_base");
		map.put("state", "123#wechat_redirect");
		return map;
	}
	
	public AccessToken accessToken(String code) {
		JSONObject json = null;
		try {
			String data = HttpClientUtils
					.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
							+ appid + "&secret=" + appsecret + "&code="+code+"&grant_type=authorization_code");
			json = JSONObject.fromObject(new String(data.getBytes("ISO8859-1"), "UTF-8"));
			if (json.containsKey("errcode")) {
				return null;
			}
			AccessToken accessToken = new AccessToken();
			accessToken.infoJSONObject(json);
			return accessToken;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public UserInfo userinfo(String accessToken, String openid) {
		JSONObject json = null;
		try {
			String data = HttpClientUtils
					.get("https://api.weixin.qq.com/sns/userinfo?access_token="
							+ accessToken + "&openid=" + openid
							+ "&lang=zh_CN");
			json = JSONObject.fromObject(new String(data.getBytes("ISO8859-1"),
					"UTF-8"));
			if (json.containsKey("errcode")) {
				return null;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.infoJSONObject(json);
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject createTicket(String actionName, String sceneId, String sceneStr) {
		JSONObject json = null;
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        ServletContext servletContext = webApplicationContext.getServletContext();
		String accessToken = servletContext.getAttribute("access_token").toString();
		String param = "{\"expire_seconds\": 604800,\"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": \""+sceneId+"\"}}}";
		try {
			String data = HttpClientUtils.post("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, param);
			json = JSONObject.fromObject(new String(data.getBytes("ISO8859-1"), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public void createMenu(String accessToken) {
		JSONObject json = null;
		String data = HttpClientUtils.post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken, menu);
		json = JSONObject.fromObject(data);
		if (json.getInt("errcode") != 0) {
			LOGGER.error("初始化微信自定义菜单失败");
		}
	}

	@Override
	public String mediaDownload(String mediaId) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        ServletContext servletContext = webApplicationContext.getServletContext();
		String accessToken = servletContext.getAttribute("access_token").toString();
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+accessToken+"&media_id=" + mediaId;
		String path = fileService.download(FileType.image, url);
		return path;
	}
	
	
	
}
