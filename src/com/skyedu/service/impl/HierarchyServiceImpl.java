package com.skyedu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.HierarchyDAO;
import com.skyedu.model.Hierarchy;
import com.skyedu.service.HierarchyService;

@Service
public class HierarchyServiceImpl implements HierarchyService{

	@Autowired
	private HierarchyDAO hierarchyDAO;
	
	@Override
	public List<Map<String,Object>> getGradeList() {
		// TODO Auto-generated method stub
		return hierarchyDAO.getGradeList();
	}

	@Override
	public List<Map<String,Object>> getSubjectList(String grade) {
		// TODO Auto-generated method stub
		return hierarchyDAO.getSubjectList(grade);
	}

	@Override
	public List<Map<String,Object>> getCateList(String grade, String subject) {
		// TODO Auto-generated method stub
		return hierarchyDAO.getCateList(grade, subject);
	}

	@Override
	public Hierarchy getHierarchy(String grade, String subject, String cate) {
		// TODO Auto-generated method stub
		return hierarchyDAO.getHierarchy(grade, subject, cate);
	}

	@Override
	public Hierarchy getHierarchy(int hierarchyId) {
		// TODO Auto-generated method stub
		return hierarchyDAO.getHierarchy(hierarchyId);
	}

	@Override
	public Map<String,Object> getHierarchyInfo(int hierarchyId){
		return hierarchyDAO.getHierarchyInfo(hierarchyId);
	}
}
