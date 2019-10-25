package com.skyedu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.AppZipDAO;
import com.skyedu.model.AppVersion;
import com.skyedu.model.AppZip;
import com.skyedu.service.AppZipService;

@Service
public class AppZipServiceImpl implements AppZipService{

	@Autowired
	private AppZipDAO appZipDAO;
	
	@Override
	public List<Map<String,Object>> getAppZipList() {
		// TODO Auto-generated method stub
		return appZipDAO.getAppZipList();
	}

	@Override
	public void updateAppZip(AppZip appZip) {
		// TODO Auto-generated method stub
		appZipDAO.updateAppZip(appZip);
	}

	@Override
	public void delAppZip(int id) {
		// TODO Auto-generated method stub
		appZipDAO.delAppZip(id);
	}

	@Override
	public void saveAppZip(AppZip appZip) {
		// TODO Auto-generated method stub
		appZipDAO.saveAppZip(appZip);
	}

	@Override
	public AppZip getAppZip(int id) {
		// TODO Auto-generated method stub
		return appZipDAO.getAppZip(id);
	}

	@Override
	public Map<String,Object> getAppVersion() {
		// TODO Auto-generated method stub
		Map<String,Object> appVersion = appZipDAO.getAppVersion();
		if (appVersion==null) {
			appVersion = new HashMap<String,Object>();
			appVersion.put("version","1.0.0");
		}else{
			List<Map<String, Object>> appContentList = appZipDAO.getAppContent((Integer)appVersion.get("id"));
			appVersion.put("appContentList", appContentList);
		}
		return appVersion;
	}

	@Override
	public void setAppVersion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		appZipDAO.setAppVersion(appVersion);
	}

}
