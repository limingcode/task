package com.skyedu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.Page;
import com.skyedu.dao.impl.UseAppStatDao;
import com.skyedu.service.UseAppStatService;

@Service
public class UseAppStatServiceImpl implements UseAppStatService {

	@Autowired
	private UseAppStatDao useAppStatDao;

	@Override
	public Page appAndWorkStat(Page page, int type, String className, String studentNum, String studentName,
			String teacherName, String grade, String loginDateStart, String loginDateEnd) {
		return useAppStatDao.appAndWorkStat(page, type, className, studentNum, studentName, teacherName, grade, loginDateStart, loginDateEnd);
	}

	@Override
	public List<Map<String, Object>> exportExcel(int type) {
		return useAppStatDao.exportExcel(type);
	}
	
	@Override
	public Map<String, Object> getLoginNum(String D1, String D2) {
		return useAppStatDao.getLoginNum(D1, D2);
	}
}
