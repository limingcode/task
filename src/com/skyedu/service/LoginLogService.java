package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.WaTeacherLoginInfo;

/**   
* 类说明
* @author  lisujing
* @version V1.0   
* @Date    2019年7月10日 下午2:30:22  
*/
public interface LoginLogService {
	void add(WaTeacherLoginInfo waTeacherLoginInfo);
	List<Map<String,Object>> getLoginLogList(Map<String,Object> condition);
}
