package com.teach.dao;

import java.util.List;
import java.util.Map;

import com.teach.Page;

public interface TpFeedbackDao {

	public List<Map<String, Object>> getFeedbackList(int lessonId);

	public Page getPage(Page page, String lessonId, String createTime, String subject, String period, String grade, String cate,
			String classTime, String name, String state);

	public Map<String, Object> getLessonInfo(int lessonId);
	
	public void deleteFeedbackByLessonId(int lessonId);

	public void deleteAttendanceRecord(int lessonId);
	
}
