package com.skyedu.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.AnalyzationDAO;
import com.skyedu.dao.impl.CategoryDAO;
import com.skyedu.service.AnalyzationService;

@Service
public class AnalyzationServiceImpl implements AnalyzationService{

	@Autowired
	private AnalyzationDAO analyzationDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Override
	public List<Map<String, Object>> getRankList(String cate, String subject, String grade, int studentId) {
		// TODO Auto-generated method stub
		return analyzationDAO.getRankList(cate, subject, grade, studentId);
	}
	
	@Override
	public List<Map<String, Object>> getCorrectList(String cate, String subject, String grade, int studentId) {
		// TODO Auto-generated method stub
		return analyzationDAO.getCorrectList(cate, subject, grade, studentId);
	}

	@Override
	public Map<String, Object> getNearlyCorrect(String cate, String subject, String grade, int studentId) {
		// TODO Auto-generated method stub
		Map<String,Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> categoryList = categoryDAO.getCategoryList();
		Map<Integer,String> condition = new HashMap<Integer, String>();
		condition.put(1, "reading");
		condition.put(2, "speaking");
		condition.put(3, "listening");
		condition.put(4, "grammar");
		condition.put(5, "vocabulary");
		for (Iterator<Map<String, Object>> iterator = categoryList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			int categoryId = (Integer) map.get("id");
			Map<String, Object> nearlyCorrect = analyzationDAO.getNearlyCorrect(categoryId, cate, subject, grade, studentId);
			if (nearlyCorrect!=null) {
				nearlyCorrect.put("categoryName", map.get("categoryName"));
				nearlyCorrect.put("category", map.get("id"));
				data.put(condition.get(categoryId), nearlyCorrect);
			}else {
				Map<String, Object> bean = new HashMap<String, Object>();
				bean.put("categoryName", map.get("categoryName"));
				bean.put("correct", 0);
				bean.put("score", 0);
				bean.put("category", map.get("id"));
				data.put(condition.get(categoryId), bean);
			}
		}
		return data;
	}

}
