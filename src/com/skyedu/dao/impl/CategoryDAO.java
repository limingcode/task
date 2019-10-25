package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.Category;
import com.util.HbmDAOUtil;

@Repository
public class CategoryDAO extends HbmDAOUtil {

	/** 获取题类列表 */
	public List<Map<String,Object>> getCategoryList(){
		String sql = "select * from WA_Category";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public Map<String,Object> getCategory(int categoryId){
		String sql = "select * from WA_Category where id="+categoryId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
	
	public Category getCategoryBean(int categoryId){
		String sql = "from Category where iD="+categoryId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (Category) q.uniqueResult();
	}
}