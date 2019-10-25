package com.teach.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.teach.Page;
import com.util.HbmDAOUtil;

/**
 * @author：longzhiwei
 * @date：2017年7月14日 下午3:13:30
 * @類說明：
 */
@Repository
public class BaseDao extends HbmDAOUtil {
	
	/**
	 * 保存对象
	 * @param obj
	 * @return
	 */
	public Serializable saveObj(Serializable obj) {
		factory.getCurrentSession().save(obj);
		return obj;
	}
	
	/**
	 * 保存或者更新对象
	 * @param obj
	 * @return
	 */
	public Serializable saveOrUpdate(Serializable obj) {
		return (Serializable) factory.getCurrentSession().merge(obj);
	}
	
	/**
	 * 使用sql查询得到List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getListUseSql(String sql){
		Query query = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	/**
	 * 使用hql查询得到对象
	 * @param hql
	 * @return
	 */
	public Serializable getObjUseHql(String hql) {
		Query query = factory.getCurrentSession().createQuery(hql);
		return (Serializable) query.uniqueResult();
	}
	
	/**
	 * 使用sql查询得到对象
	 * @param hql
	 * @return
	 */
	public Serializable getObjUseSql(String sql) {
		Query query = factory.getCurrentSession().createSQLQuery(sql);
		return (Serializable) query.uniqueResult();
	}
	
	/**
	 * 使用Hql查询得到List<Serializable>
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Serializable> getListUseHql(String hql) {
		Query query = factory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	/**
	 * 使用sql查询得到Map<String, Object>
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMapUseSql(String sql) {
		Query query = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) query.uniqueResult();
	}
	
	/**
	 * 使用sql更新
	 * @param sql
	 */
	public void updateUseSql(String sql) {
		Query query = factory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	/**
	 * 分页查询
	 * @param hql
	 * @param page
	 * @return
	 */
	@SuppressWarnings({"unchecked" })
	public List<Map<String, Object>> getPageListUseSql(String sql, Page page) {
		Query query = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		//每页条数
		query.setMaxResults(page.getPageSize());
		// 设置起点
		query.setFirstResult((page.getCurrPage() -1) * page.getPageSize());
		return query.list();
	}
	
	/**
	 * 获取总记录数
	 * @param hql
	 * @return
	 */
	public int getCount(String sql) {
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		return Integer.parseInt(q.list().get(0).toString());
	}
	
	/**
	 * 保存map对象
	 * @param tableName
	 * @param map
	 */
	public void saveMap(String tableName, Map<String, Object> map) {
		StringBuffer colStr = new StringBuffer();
		StringBuffer valStr = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if(entry.getValue() != null) {
				colStr.append(entry.getKey() + ",");
				valStr.append("'"+ entry.getValue() + "',");
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append("insert into "+ tableName +" (")
		.append(StringUtils.substring(colStr.toString(), 0, colStr.length()-1))
		.append(") values(")
		.append(StringUtils.substring(valStr.toString(), 0, valStr.length()-1))
		.append(")");
		updateUseSql(sql.toString());
	}
}
