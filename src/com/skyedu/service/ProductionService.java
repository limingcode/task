package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.Production;

public interface ProductionService {

	void saveProduction(Production production);
	
	List<Map<String,Object>> getProductionList(int studentId, int pageNo);
	
	List<Map<String,Object>> getAttachmentList(int productionId);
	
	void delProduction(int StudentId,int productionId);
}
