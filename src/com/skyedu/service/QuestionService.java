package com.skyedu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.skyedu.model.Hierarchy;
import com.skyedu.model.Question;

public interface QuestionService {

	Question getQuestion(int id);

	void saveQuestion(Question question);
	
	void updateQuestion(Question question);

	List<Map<String, Object>> quesList(Map<String, Object> condition);

	List<Map<String, Object>> childList(int parentId);

	List<Map<String, Object>> getQuestionByWork(int workId,String brief);

	void saveQuestionHtml(int questionId, String qItemCnt);

	void updateQuestionHtml(int questionId, String qItemCnt);

	Map<String, Object> getQuestionHtml(int questionId);
	
	void deleteResults(int questionId);
	
	void countScore(int parentId);
	
	boolean delQuestion(int questionId);
	
	Map<String,Object> saveQuestions(List<Map<String, Object>> qItemList,int oaId,Hierarchy hierarchy,String appPath,String quesPath, String quesOriPath);
	
	Map<String,Object> saveQuestionsEx(HttpServletRequest request, String appPath, String quesPath,
			String quesOriPath, String quesOriTempPath, List<Map<String, Object>> parse, String grade, String subject,
			String cate, String parentId);
}
