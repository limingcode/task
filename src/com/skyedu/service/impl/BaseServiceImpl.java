package com.skyedu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.BaseDAO;
import com.skyedu.service.BaseService;

@Service
public class BaseServiceImpl implements BaseService {

	@Autowired
	private BaseDAO baseDAO;
	
	@Override
	public List<Map<String, Object>> gradeList() {
		return baseDAO.gradeList();
	}

	@Override
	public List<Map<String, Object>> subjectList() {
		return baseDAO.subjectList();
	}

	@Override
	public List<Map<String, Object>> cateList() {
		// TODO Auto-generated method stub
		return baseDAO.cateList();
	}

	@Override
	public List<Map<String, Object>> courseList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseDAO.courseList(map);
	}

	@Override
	public List<Map<String, Object>> periodList() {
		// TODO Auto-generated method stub
		return baseDAO.periodList();
	}

	@Override
	public List<Map<String, Object>> depaList() {
		// TODO Auto-generated method stub
		return baseDAO.depaList();
	}

	@Override
	public List<Map<String, Object>> myHierarchys(int student) {
		// TODO Auto-generated method stub
		return baseDAO.myHierarchys(student);
	}

	@Override
	public Map<String, Object> getCourse(int courseId) {
		// TODO Auto-generated method stub
		return baseDAO.getCourse(courseId);
	}

	@Override
	public Map<String, Object> getTeacher(String teacherName, String tel) {
		// TODO Auto-generated method stub
		return baseDAO.getTeacher(teacherName, tel);
	}

	@Override
	public Map<String, Object> teacherInfo(Integer oaId) {
		// TODO Auto-generated method stub
		return baseDAO.teacherInfo(oaId);
	}

}
