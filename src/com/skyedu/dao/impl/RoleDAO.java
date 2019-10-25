package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.skyedu.model.AppVersion;
import com.skyedu.model.AppZip;
import com.skyedu.model.UserRole;
import com.util.HbmDAOUtil;

@Repository
public class RoleDAO extends HbmDAOUtil{

	public List<String> getRoleList(){
		String sql = "select roleUrl from WA_Role where state!=-1";
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		return q.list();
	}
	
	public List<Map<String,Object>> getRoleMapList(){
		String sql = "select * from WA_Role where state=0 order by pid,id";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public List<String> getUserRoleList(int oaId){
		String sql = "select a.roleUrl from WA_Role a left join WA_UserRole b on a.id=b.roleId  where a.state!=-1 and b.state=1 and oaId="+oaId;
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		return q.list();
	}
	
	public List<Map<String,Object>> getEmployeeList(Map<String,Object> condition){
		Object username = condition.get("username");
		Object deptName = condition.get("deptName");
		Integer pageNo = (Integer) condition.get("pageNo");
		Integer pageSize = (Integer) condition.get("pageSize");
		String sql = "select a.oaId,a.employeeName,b.deptName,(select d.roleUrl+',' from WA_UserRole c left join WA_Role d on c.roleId=d.id where c.state=1 and c.oaId=a.oaId for xml path(''))roleUrls from [SvrEdu].SkyData.dbo.OA_Employee a left join [SvrEdu].SkyData.dbo.OA_Department b on a.deptId = b.deptId where a.status!=5 ";
		String sql1 = "select count(1) count from [SvrEdu].SkyData.dbo.OA_Employee a left join [SvrEdu].SkyData.dbo.OA_Department b on a.deptId = b.deptId where a.status!=5  ";
		if (username!=null && !StringUtils.isEmpty((String)username)) {
			sql = sql + " and a.employeeName like '%"+username+"%'";
			sql1 = sql1 + " and a.employeeName like '%"+username+"%'";
		}
		if (deptName!=null && !StringUtils.isEmpty(deptName)) {
			sql = sql + " and b.deptName like '%"+deptName+"%'";
			sql1 = sql1 + " and b.deptName like '%"+deptName+"%'";
		}
		sql = sql + " order by b.deptLevel,b.deptId,a.oaId";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
		Query q1 = factory.getCurrentSession().createSQLQuery(sql1).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int count = (Integer) ((Map<String,Object>)q1.uniqueResult()).get("count");
		int totalPage = count%pageSize==0?count/pageSize:count/pageSize+1;
		condition.put("count", count);
		condition.put("totalPage", totalPage);
		return q.list();
	}
	
	public UserRole getUserRole(int oaId,int roleId){
		String sql = "from UserRole where oaId="+oaId+" and roleId="+roleId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (UserRole) q.uniqueResult();
	}
	
	public boolean saveOrUpdateUserRole(UserRole userRole){
		factory.getCurrentSession().saveOrUpdate(userRole);
		return true;
	}
}
