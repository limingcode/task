package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.Page;

public interface UseAppStatService {
	
	public Page appAndWorkStat(Page page, int type, String className, String studentNum, String studentName,
			String teacherName, String grade, String loginDateStart, String loginDateEnd);

	public List<Map<String, Object>> exportExcel(int type);

	public Map<String, Object> getLoginNum(String D1, String D2);

}
