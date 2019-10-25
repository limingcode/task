package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONObject;

public class SendHttpUrl {

	private static final String URL = "http://192.168.100.50:10080/auth";
	public static String PWD = "http://192.168.218.55/Auth/Auth/";
	
	public static String send(String url){
		if(null == url || "".equals(url.trim())) url = URL;
		StringBuilder result = new StringBuilder();  
		try{  
            HttpURLConnection urlConnection = (HttpURLConnection)new URL(url).openConnection();  
            urlConnection.setRequestMethod("GET");  
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String line = null;  
            try {  
                while((line = bufferedReader.readLine()) != null){  
                	result.append(code(line));
                	//result.append( + "\n");  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                try{  
                    inputStreamReader.close();  
                    inputStream.close();  
                    bufferedReader.close();  
                }catch(IOException e){  
                    e.printStackTrace();  
                }  
            }  
        }catch(IOException e){  
        }  
		return result.toString();
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Object> sendJson(String url){
		String str = send(url);
		System.out.println(url);
		System.out.println(str);
		return JSONObject.fromObject(str);
	}
	
	
	public static Map<String, Object> sendPwd(int menuId,String userId, String userName, String pwd){
		if(null != userName && !"".equals(userName.trim()))
			userName = URLEncoder.encode(userName);
		if(null != pwd && !"".equals(pwd.trim()))
			pwd = URLEncoder.encode(pwd);
		return sendJson(PWD+"?userId="+userId+"&user="+userName+"&key="+pwd+"&escape=1&menuId="+menuId);
	}
	
	public static String code(String value){
		String str = "";
		try {
			str = URLDecoder.decode(value, "UTF-8"); //对str根据UTF-8执行URL解码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String(str);
	}
	
	public static void main(String[] args) {  
		Map<String, Object> map = SendHttpUrl.sendPwd(0,null, "dlcl7vLb4H9XnKbgO4bIww==", "1B8J7lbPhV%2B2g9B6m65XIQCmkgM=");
		 System.out.println(map);
    }  
}
