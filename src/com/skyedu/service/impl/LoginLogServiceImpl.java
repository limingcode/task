package com.skyedu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.LoginLogDao;
import com.skyedu.model.WaTeacherLoginInfo;
import com.skyedu.service.LoginLogService;

/**   
* 类说明
* @author  lisujing
* @version V1.0   
* @Date    2019年7月10日 下午2:30:38  
*/
@Service
public class LoginLogServiceImpl implements LoginLogService {
	@Resource
	private LoginLogDao loginLogDao;
	
	@Override
	public void add(WaTeacherLoginInfo waTeacherLoginInfo) {
		loginLogDao.save(waTeacherLoginInfo);
	}

	@Override
	public List<Map<String, Object>> getLoginLogList(Map<String, Object> condition) {
		List<Map<String, Object>> list=loginLogDao.getLoginLogList(condition);
		for(int i=0;i<list.size();i++) {
			Map<String, Object> map=list.get(i);
			List<Map<String,Object>> timeList=splitTimes(map.get("times"));
			map.put("loginTime", timeList);
			if(map.get("times")!=null) {
				map.put("loginNum", map.get("times").toString().split("@").length);
			}else {
				map.put("loginNum", 0);
			}
		}
		return list;
	}
	
	private List<Map<String,Object>> splitTimes(Object strings){
		if (strings == null) {
			return Collections.emptyList();
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String times[]=strings.toString().split("@");
		for(int i=0;i<times.length;i++) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("time", times[i]);
			list.add(map);
		}
		return list;
	}

}
