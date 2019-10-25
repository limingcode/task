/*
 * @(#)HbmDAO.java Time: 2013-1-3
 *
 * Copyright 2013 xuedou.com All rights reserved.
 */

package com.util;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *<pre>类说明</pre>
 *<b>功能描述：</b>
 * 主要提供有,对对象的增删改查. 这个类交给spring来管理
 * 注意事项：
 * 并且该类的方法具备有事务回滚功能,但是关于查找的已经取消事务回滚
 * @author  meiguanghui meiguanghui@xuedou.com
 * @version 1.0, 2013-1-4
 */
@Repository @Transactional
public class HbmDAOUtil {
	@Autowired(required=true)@Qualifier("sessionFactory")
	public SessionFactory factory;		//为这个成员变量注入值
	
	/**
	 * 保存对象
	 * @param obj 需要执行保存的对象
	 */
	public void save(Object obj) {
		factory.getCurrentSession().persist(obj); 	//保存
	}
	
	/**
	 * 修改对象
	 * @param obj 需要执行保存的对象
	 */
	public void update(Object obj) {
		factory.getCurrentSession().merge(obj); 	//类似saveOrUpdate
		//factory.getCurrentSession().update(obj);
	}
	
	/**
	 * 删除对象
	 * @param obj 需要删除的对象
	 * @param ids 这是个可变参数, 可以传入n个id
	 */
	public void delete(Class<?> cla,Integer... ids) {
		for(int id:ids)
		{
			factory.getCurrentSession().delete(factory.getCurrentSession().load(cla, id));
		}
	}
	
	/**
	 * 根据id找出对象 ,这个方法不具备事务回滚功能
	 * @param obj 需要查找的对象
	 * @param id id值
	 * @return 这个传进去的对象
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Object find(Class<?> cla,int id) {
		return factory.getCurrentSession().get(cla, id);
	}
	
	/**
	 * 找出指定类的所有对象
	 * @param className 类的名称(不包含包)
	 * @return 返回操作的对象
	 */
	@SuppressWarnings({ "rawtypes" })
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List list(String className) {
		return factory.getCurrentSession().createQuery("from "+className).list();
	}
	
	public void showCacheMes(){
		//获取缓存统计
		Statistics st=factory.getStatistics();
		Logger.getLogger(HbmDAOUtil.class).info("二级缓存的区域名字:"+st.getSecondLevelCacheRegionNames());
		Logger.getLogger(HbmDAOUtil.class).info("二级缓存放入的次数:"+st.getSecondLevelCachePutCount());
		Logger.getLogger(HbmDAOUtil.class).info("二级缓存的命中次数:"+st.getSecondLevelCacheHitCount());
		Logger.getLogger(HbmDAOUtil.class).info("错过的次数:"+st.getSecondLevelCacheMissCount());
	}
	
	
	/**
	 * countQl语句处理
	 * @param ql 传入一个正常的QL语句通过处理获得select count(*)类似的count语句
	 * @return
	 */
	public static String getCountQL(String ql){
		StringBuffer sql = new StringBuffer("select count (*) ");
		if(Validator.validateRequired(ql)){
			/*ql = ql.substring(ql.indexOf(" from "));
			int g = ql.lastIndexOf(" group by ");
			int d = ql.lastIndexOf("=");
			if(ql.lastIndexOf(" pivot")>d) g=-1;
			if(g>d) ql = ql.substring(0, g);
			int o = ql.lastIndexOf(" order by ");
			if(o>d) ql = ql.substring(0, o);
			sql.append(ql);*/
			String seql = ql.substring(0,ql.indexOf("select "));
			ql = ql.substring(ql.indexOf(" from "));
			int d = 0;//ql.lastIndexOf("=");
			int s = ql.lastIndexOf(" where ");
			if(s>d)d=s;
			s = ql.lastIndexOf(" pivot");
			if(s>d)d=s;
			int o = ql.lastIndexOf(" order by ");
			if(o>d) ql = ql.substring(0, o);
			sql.append("from (select 1 as functionByGetCount "+ql+" ) functionByGetCountQl");
			sql = new StringBuffer(seql+sql.toString());
		}
		return sql.toString();
	}
	
	/**
	 * countQl语句处理(返回结果中不允许出现重复列名)
	 * @param ql 传入一个正常的QL语句通过处理获得select count(*)类似的count语句
	 * @return
	 */
	public static String getCountQL1(String ql){
		StringBuffer sql = new StringBuffer("select count (*) ");
		if(Validator.validateRequired(ql)){
			String seql = ql.substring(0,ql.indexOf("select "));
			//ql = ql.substring(ql.indexOf(" from "));
			int d = 0;//ql.lastIndexOf("=");
			int s = ql.lastIndexOf(" where ");
			if(s>d)d=s;
			s = ql.lastIndexOf(" pivot");
			if(s>d)d=s;
			int o = ql.lastIndexOf(" order by ");
			if(o>d) ql = ql.substring(0, o);
			sql.append("from ( "+ql.substring(ql.indexOf("select "))+" ) functionByGetCountQl");
			sql = new StringBuffer(seql+sql.toString());
		}
		return sql.toString();
	}
	
	/**
	 * sumQl语句处理
	 * @param 
	 * 	实例：XXX1@@YYY1,XXX2@@YYY2 当参数中不包含@@时YYY=XXX
	 * 	ql 传入一个正常的QL语句通过处理获得select sum(XXX1) as YYY1,sum(XXX2) as YYY2,.....类似的SUM语句
	 * @param appendSQL 返回结果集中拼接的sql (以逗号“,”结尾)
	 * @return
	 */
	public static String getSumQL(String ql, String appendSQL, String ...rets){
		StringBuffer sql = new StringBuffer("select ");
		if(Validator.validateRequired(ql)){
			String seql = ql.substring(0,ql.indexOf("select "));
			for(String retInfo : rets){
				if(null == retInfo) retInfo = "";
				String[] fs = retInfo.split("@@");
				String f1=fs[0];
				String f2=f1;
				if(fs.length>1) f2=fs[1];
				sql.append("SUM(").append(f1).append(") AS ").append(f2).append(",");
			}
			if(!StringUtil.isEmpty(appendSQL))
				sql.append(appendSQL.trim());
			sql.append(" 'sumQl语句' as [functionByGetSumQl] ");
			int d = 0;//ql.lastIndexOf("=");
			int s = ql.lastIndexOf(" where ");
			if(s>d)d=s;
			s = ql.lastIndexOf(" pivot");
			if(s>d)d=s;
			int o = ql.lastIndexOf(" order by ");
			if(o>d) ql = ql.substring(0, o);
			sql.append(" from ( "+ql.substring(ql.indexOf("select "))+" ) functionByGetSumQl");
			sql = new StringBuffer(seql+sql.toString());
		}
		return sql.toString();
	}
	

	/**
	 * 判断某字符串是否为空或''值
	 * @param str 传入String串
	 * @return 返回Boolean值 true表示为空或'' false则反之
	 */
	public static boolean isEmpty(String str){
		boolean bol = false;
		if(null == str || "".equals(str.trim())){
			bol = true;
		}
		return bol;
	}
}
