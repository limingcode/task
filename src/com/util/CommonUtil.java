package com.util;

import java.util.HashMap;
import java.util.Map;


public class CommonUtil {

	//消息列表
	public static final int MESSAGESIZE = 20;
	
	//题目列表
	public static final int QUESTIONSIZE = 10;
	
	//排行榜列表
	public static final int RANKSIZE = 20;
	
	//错题集列表
	public static final int MISTAKESIZE = 20;
	
	//个人作品列表
	public static final int PRODUCTIONSIZE = 20;
	
	public static final String LOGINURL = "http://192.168.218.55:880/Auth/SkyLogin";
	
	public static final String COURSETIMEURL = "http://192.168.218.55:8080/Att/pStudAttendance/";
	
	public static final String APPSOCKETURL = "http://seesfile.skyedu99.com/AppTask/upload/fileReceive.jhtml";
	
	public static final String APPCLIENTURL = "http://teaching.skyedu99.com";
	
	public static final String APPFILEURL = "http://seesfile.skyedu99.com";
	
	public static final String APPFILEWEB = "http://seesfile.skyedu99.com";
	
//	public static final String APPSOCKETURL = "http://120.79.99.50:9081/AppTask/upload/fileReceive.jhtml";
//	public static final String APPSOCKETURL = "http://182.61.21.142:9081/AppTask/upload/fileReceive.jhtml";
	//public static final String APPSOCKETURL = "http://192.168.40.17:8087/AppTask/upload/fileReceive.jhtml";
    /**
     *
     */
	private Map<String, Object> config = new HashMap<String, Object>();

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

}
