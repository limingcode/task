package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.Hierarchy;

public interface HierarchyService {

	List<Map<String,Object>> getGradeList();
	
	List<Map<String,Object>> getSubjectList(String grade);
	
	List<Map<String,Object>> getCateList(String grade, String subject);
	
	Hierarchy getHierarchy(String grade, String subject, String cate);
	
	Hierarchy getHierarchy(int hierarchyId);
	
	/** 综合层次 */
	Map<String,Object> getHierarchyInfo(int hierarchyId);
}
