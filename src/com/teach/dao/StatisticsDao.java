package com.teach.dao;

import java.util.List;
import java.util.Map;

import com.teach.Page;

public interface StatisticsDao {

	public Page getLessonUploadStatus(Page page, String subject, String period, String grade, String cate);

	List<Map<String, Object>> getLessonUploadStatus(String subject, String period);
}
