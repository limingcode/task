package com.skyedu.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.skyedu.sdk.wechat.model.JSSignature;
import com.skyedu.service.WeChatJSService;
import com.util.HttpClientUtils;

/**
 * 微信JSImpl
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-22
 */
@Service
public class WeChatJSServiceImpl implements WeChatJSService{

	@Override
	public String getJsapiTicket(String accessToken) {
		try {
			String data = HttpClientUtils
					.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
							+ accessToken + "&type=jsapi");
			JSONObject json = JSONObject.fromObject(data);
			String ticket = json.getString("ticket");
			return ticket;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JSSignature getJSSignature(String url) {
		JSSignature jsSignature = new JSSignature();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        ServletContext servletContext = webApplicationContext.getServletContext();
		String jsapiTicket = servletContext.getAttribute("jsapi_ticket").toString();
		if (url == null) {
            return null;
        }
		int index = url.indexOf("#");
        if (index > 0) {
            url = url.substring(0, index);
        }
        String noncestr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = "";
        String string1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        jsSignature.setSignature(signature);
        jsSignature.setTimestamp(timestamp);
        jsSignature.setNonceStr(noncestr);
        jsSignature.setUrl(url);
		return jsSignature;
	}
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
}
