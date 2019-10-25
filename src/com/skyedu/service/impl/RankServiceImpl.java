package com.skyedu.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.RankDAO;
import com.skyedu.service.RankService;

@Service
public class RankServiceImpl implements RankService{

	@Autowired
	private RankDAO rankDAO;
	
	@Override
	public int getRankCount(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return rankDAO.getRankCount(condition);
	}

	@Override
	public void setDesigntion(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		rankDAO.setDesigntion(condition);
	}

	@Override
	public List<Map<String, Object>> getStudentList(
			Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return rankDAO.getStudentList(condition);
	}

	@Override
	public Map<String, Object> getStudent(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return rankDAO.getStudent(condition);
	}

	@Override
	public List<Map<String, Object>> getRankStudentList(
			Map<String, Object> condition) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> rankStudentList = rankDAO.getRankStudentList(condition);
		return rankStudentList;
	}

	@Override
	public List<Map<String, Object>> getDesignationList(int studentId) {
		// TODO Auto-generated method stub
		return rankDAO.getDesignationList(studentId);
	}

}
