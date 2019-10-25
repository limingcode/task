package com.teach.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.teach.dao.PrepareLessonDao;
import com.teach.service.PrepareLessonService;

@Service
public class PrepareLessonServiceImpl implements PrepareLessonService {
	
	@Resource
	private PrepareLessonDao prepareLessonDao;

	@Override
	public List<Map<String, Object>> getLessonStatus(String period, String grade, String subject, String cate) {
		return prepareLessonDao.getLessonStatus(period, grade, subject, cate);
	}

}
