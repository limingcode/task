package com.teach.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teach.Page;
import com.teach.dao.TpFeedbackDao;
import com.teach.service.TpFeedbackService;

/**
 * 课后反馈service
 * @author huangdebin
 * @version 1.0
 */
@Service
public class TpFeedbackServiceImpl implements TpFeedbackService {

	@Autowired
	private TpFeedbackDao feedbackDao;
	
	/**
	 * 得到反馈列表
	 */
	@Override
	public List<Map<String, Object>> getFeedbackList(int lessonId) {
		return feedbackDao.getFeedbackList(lessonId);
	}

	/**
	 * 通过查询条件得到反馈列表
	 */
	@Override
	public Page getPage(Page page, String lessonId, String createTime, String subject, String period, String grade, String cate,
			String classTime, String name, String state) {
		return feedbackDao.getPage(page, lessonId, createTime, subject, period, grade, cate, classTime, name, state);
	}

	/**
	 * 得到课次信息
	 */
	@Override
	public Map<String, Object> getLessonInfo(int lessonId) {
		return feedbackDao.getLessonInfo(lessonId);
	}

}
