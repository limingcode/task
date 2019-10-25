package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.AppVersion;
import com.skyedu.model.AppZip;
import com.util.HbmDAOUtil;

@Repository
public class AppZipDAO extends HbmDAOUtil{

	public List<Map<String,Object>> getAppZipList(){
		String sql = "select * from WA_AppZip where count<=4";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public AppZip getAppZip(int id){
		String sql = "from AppZip where workId="+id;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (AppZip) q.uniqueResult();
	}
	
	public void updateAppZip(AppZip appZip){
		factory.getCurrentSession().update(appZip);
	}
	
	public void delAppZip(int id){
		String sql = "delete from WA_AppZip where id="+id;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void saveAppZip(AppZip appZip){
		factory.getCurrentSession().save(appZip);
	}
	
	public Map<String,Object> getAppVersion(){
		String sql = "select top 1 * from WA_AppVersion order by id desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String,Object>) q.uniqueResult();
	}
	
	public List<Map<String,Object>> getAppContent(int versionId){
		String sql = "select * from WA_AppContent where appVersion="+versionId+" order by id";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	public void setAppVersion(AppVersion appVersion){
		factory.getCurrentSession().save(appVersion);
	}
}
