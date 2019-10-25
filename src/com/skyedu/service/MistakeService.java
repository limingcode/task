package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.Mistake;
import com.skyedu.model.WorkInfo;

public interface MistakeService {

	void saveMistake(Mistake mistake);
	
	List<Map<String,Object>> getMistakeList(Map<String,Object> condition);
	
	void delMistakes(Mistake mistake);
	
	void delMistakeByWork(int workId);
	
	void updateMistake(Mistake mistake);
}
