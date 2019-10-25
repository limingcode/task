package com.skyedu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.CategoryDAO;
import com.skyedu.model.Category;
import com.skyedu.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Override
	public List<Map<String, Object>> getCategoryList() {
		return categoryDAO.getCategoryList();
	}

	@Override
	public Map<String, Object> getCategory(int categoryId) {
		return categoryDAO.getCategory(categoryId);
	}

	@Override
	public Category getCategoryBean(int categoryId) {
		return categoryDAO.getCategoryBean(categoryId);
	}

	
}
