package com.image.tag.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author：longzhiwei
 * @date：2017年7月14日 下午3:13:30
 * @類說明：
 */
@Repository
@Transactional
public class ImageTagBaseDao{
	@Autowired(required=true)@Qualifier("sessionFactory")
	public SessionFactory factory;		//为这个成员变量注入值
	
	/**
	 * 保存对象
	 * @param obj
	 * @return
	 */
	public Serializable saveObj(Serializable obj) {
		factory.getCurrentSession().save(obj);
		return obj;
	}

//	public void saveOrUpdateObj(Object obj){
//	    factory.getCurrentSession().saveOrUpdate(obj);
//    }
//
	/**
	 * 根据id找出对象 ,这个方法不具备事务回滚功能
	 * @param cla 需要查找的对象
	 * @param id id值
	 * @return 这个传进去的对象
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Object find(Class<?> cla,int id) {
		return factory.getCurrentSession().get(cla, id);
	}

    public void delete(Class<?> cla,int id) {
         factory.getCurrentSession().delete(factory.getCurrentSession().load(cla, id));
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
    public List<Map<String, Object>> getListUseSql(String sql,int page,int pageSize){
        Query query = factory.getCurrentSession().createSQLQuery(sql).setMaxResults(pageSize).setFirstResult((page-1)*pageSize).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
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
	 * 使用Hql查询得到List<Serializable>
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Serializable> getListUseHql(String hql) {
		Query query = factory.getCurrentSession().createQuery(hql);
		return query.list();
	}
    public Object getList(String sql ) {
        Query q = factory.getCurrentSession().createQuery(sql);
        return q.list();
    }
    public Object getObject(String sql) {
        Query query = factory.getCurrentSession().createQuery(sql);
        return query.uniqueResult();
    }

    /**
	 * 使用sql查询得到Map<String, Object>
	 * @param sql
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
	 * 获取总记录数
	 * @param sql
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
