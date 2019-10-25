package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.Production;
import com.util.CommonUtil;
import com.util.HbmDAOUtil;

@Repository
public class ProductionDAO extends HbmDAOUtil {

	public void saveProduction(Production production) {
		factory.getCurrentSession().save(production);
	}

	public List<Map<String,Object>> getProductionList(int studentId,int pageNo) {
		String sql = "select id iD,* from WA_Production where student="+studentId+" order by createDate desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setFirstResult((pageNo-1)*CommonUtil.PRODUCTIONSIZE).setMaxResults(CommonUtil.PRODUCTIONSIZE).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = q.list();
		return list;
	}
	
	public List<Map<String,Object>> getAttachmentList(int productionId) {
		String sql = "select id iD,* from WA_Production_A where production="+productionId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = q.list();
		return list;
	}
	
	public void delProduction(int StudentId,int productionId) {
		String sql1 = "delete wpa from WA_Production_A wpa LEFT JOIN WA_Production wp on wpa.production= wp.id where wp.student="+StudentId+" and wp.id="+productionId;
		factory.getCurrentSession().createSQLQuery(sql1).executeUpdate();
		String sql2 = "delete from WA_Production where student="+StudentId+" and id="+productionId;
		factory.getCurrentSession().createSQLQuery(sql2).executeUpdate();
	}
	
	/*public void loveProduction(int productionId){
		String sql = "update WA_Production set produntion";
	}*/
}
