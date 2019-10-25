package com.skyedu.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.ProductionDAO;
import com.skyedu.model.Production;
import com.skyedu.service.ProductionService;

@Service
public class ProductionServiceImpl implements ProductionService{

	@Autowired
	private ProductionDAO productionDAO;
	
	@Override
	public void saveProduction(Production production) {
		// TODO Auto-generated method stub
		productionDAO.saveProduction(production);
	}

	@Override
	public List<Map<String,Object>> getProductionList(int studentId,int pageNo) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> productionList = productionDAO.getProductionList(studentId,pageNo);
		for (Iterator<Map<String, Object>> iterator = productionList.iterator(); iterator.hasNext();) {
			Map<String, Object> production = (Map<String, Object>) iterator.next();
			List<Map<String, Object>> attachmentList = productionDAO.getAttachmentList((Integer) production.get("iD"));
			production.put("attachments", attachmentList);
		}
		return productionList;
	}

	@Override
	public List<Map<String,Object>> getAttachmentList(int productionId)  {
		// TODO Auto-generated method stub
		return productionDAO.getAttachmentList(productionId);
	}
	
	@Override
	public void delProduction(int StudentId, int productionId) {
		// TODO Auto-generated method stub
		productionDAO.delProduction(StudentId, productionId);
	}

}
