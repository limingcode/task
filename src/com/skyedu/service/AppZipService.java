package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.AppVersion;
import com.skyedu.model.AppZip;

public interface AppZipService {

	List<Map<String,Object>> getAppZipList();
	
	void updateAppZip(AppZip appZip);
	
	void delAppZip(int id);
	
	void saveAppZip(AppZip appZip);
	
	AppZip getAppZip(int id);
	
	Map<String,Object> getAppVersion();
	
	void setAppVersion(AppVersion appVersion);

}
