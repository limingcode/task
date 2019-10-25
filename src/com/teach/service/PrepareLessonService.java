package com.teach.service;

import java.util.List;
import java.util.Map;

public interface PrepareLessonService {

	List<Map<String, Object>> getLessonStatus(String period, String grade, String subject, String cate);
	
}
