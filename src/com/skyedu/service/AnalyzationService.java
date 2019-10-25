package com.skyedu.service;

import java.util.List;
import java.util.Map;

public interface AnalyzationService {

	List<Map<String,Object>> getRankList(String cate,String subject,String grade ,int studentId);

	List<Map<String, Object>> getCorrectList(String cate, String subject, String grade, int studentId);
	
	Map<String, Object> getNearlyCorrect(String cate,String subject,String grade ,int studentId);
}
