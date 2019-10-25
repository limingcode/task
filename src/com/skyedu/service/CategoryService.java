package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.Category;

public interface CategoryService {

	List<Map<String,Object>> getCategoryList();
	
	Map<String,Object> getCategory(int categoryId);
	
	Category getCategoryBean(int categoryId);
}
