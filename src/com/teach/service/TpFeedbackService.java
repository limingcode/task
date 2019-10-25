package com.teach.service;

import java.util.List;
import java.util.Map;

import com.teach.Page;

public interface TpFeedbackService {

	public List<Map<String, Object>> getFeedbackList(int lessonId);

	public Page getPage(Page page, String lessonId, String createTime, String subject, String period, String grade, String cate,
			String classTime, String name, String state);

	public Map<String, Object> getLessonInfo(int lessonId);
	
}
