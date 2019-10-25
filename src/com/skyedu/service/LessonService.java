package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.Lesson;
import com.skyedu.model.LessonBean;

public interface LessonService {

	Lesson getLessonWorks(int lessonId);
	
	List<Map<String,Object>> getLessonResultCardList(int lessonId , int studentId);
	
	List<Map<String,Object>> getLessonResultCards(int lessonId, int studentId);
	
	List<Map<String,Object>> getLessonList(String period, String grade, String subject, String cate);
	
	Map<String, Object> getLessonInfo(int lessonId);
	
	LessonBean getLessonBean(int lessonId);
	
	void commitLesson(Map<String,Object> lesson);
	
	Map<String,Object> getMaxLesson(int hierarchyId, String periodId);
	
	void updateLesson(int lessonId);
	
	void updateOpenTime(int lessonId,String openTime);
	
	List<Map<String,Object>> getLessonListByCourse(int courseId);
	
	Map<String, Object> preview(int questionId);

	void updateBrief(int lessonId, String brief);

	void deleteMaxLesson(int lessonId);
}
