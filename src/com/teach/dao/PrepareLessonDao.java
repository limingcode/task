package com.teach.dao;

import java.util.List;
import java.util.Map;

public interface PrepareLessonDao {

	List<Map<String, Object>> getLessonStatus(String period, String grade, String subject, String cate);

}
