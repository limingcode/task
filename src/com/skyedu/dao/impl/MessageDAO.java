package com.skyedu.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.skyedu.model.Message;
import com.skyedu.model.MessageS;
import com.util.CommonUtil;
import com.util.HbmDAOUtil;

@Repository
public class MessageDAO extends HbmDAOUtil {

	public void saveMessage(Message message) {
		factory.getCurrentSession().save(message);
	}

	public void saveMessageS(MessageS messageS) {
		factory.getCurrentSession().save(messageS);
	}

	public List<Map<String, Object>> getMessageList(int studentId, int state, int pageNo) {
		String sql = "select wms.id iD,ec.name courseName,wms.state,wm.createDate,wm.message,wm.title,et.name teacherName from WA_Message_S wms left join WA_Message wm on wms.message=wm.id LEFT JOIN edu_course ec on ec.id=wm.course LEFT JOIN Edu_Teacher et on et.oaId=wm.sender where wm.course is not null and wms.student="
				+ studentId;
		if (state != 0) {
			sql = sql + " and wms.state=" + (state - 1);
		}
		sql = sql + " order by wm.createDate desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult((pageNo-1)*CommonUtil.MESSAGESIZE).setMaxResults(CommonUtil.MESSAGESIZE);
		return q.list();
	}

	public List<Map<String, Object>> getMessageList(Map<String,Object> condition) {
		String sql = "select uuid,max(wm.createDate) createDate,wm.message,wm.title,wm.openTime,(select name from Edu_Teacher et where et.oaId=wm.sender)teacherName,stuff((select ','+ec.name from Edu_Course ec where ec.id in (select wmm.course from WA_Message wmm where wmm.uuid=wm.uuid ) for xml path('')),1,1,'') courseList from WA_Message wm  where wm.status is null ";
		String startDate = (String) condition.get("startDate");
		String endDate = (String) condition.get("endDate");
		String pageNoo = (String) condition.get("pageNo");
		if (!StringUtils.isEmpty(startDate)) {
			sql = sql + " and createDate>'"+startDate+"' ";
		}
		if (!StringUtils.isEmpty(endDate)) {
			sql = sql + " and createDate<'"+endDate+"' ";
		}
		sql = sql + " GROUP BY wm.uuid,wm.sender,wm.message,wm.title,wm.openTime order by createDate desc";
		
		int pageNo=1;
		if (!StringUtils.isEmpty(pageNoo)) {
			pageNo = Integer.valueOf(pageNoo);
		}
		
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult((pageNo-1)*CommonUtil.MESSAGESIZE).setMaxResults(CommonUtil.MESSAGESIZE);
		
		String countSql = "select count(1) count from WA_Message wm where wm.status is null ";
		if (!StringUtils.isEmpty(startDate)) {
			countSql = countSql + " and createDate>'"+startDate+"' ";
		}
		if (!StringUtils.isEmpty(endDate)) {
			countSql = countSql + " and createDate<'"+endDate+"' ";
		}
		countSql = countSql + " GROUP BY wm.uuid,wm.sender,wm.message,wm.title,wm.openTime";
		Query countQ = factory.getCurrentSession().createSQLQuery(countSql);
		int size = countQ.list().size();
		int totalPage = size / CommonUtil.MESSAGESIZE + 1;
		if (size % CommonUtil.MESSAGESIZE == 0) {
			totalPage = totalPage - 1;
		}
		if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		condition.put("pageNo", pageNo);
		condition.put("totalPage", totalPage);
		return q.list();
	}

	public void delMessage(int id) {
		String sql = "delete from WA_Message_S where id=" + id;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	public void updateMessage(int id) {
		String sql = "update WA_Message_S set state=1 where id=" + id;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public int getUnreadCount(int studentId){
		String sql = "select count(1) count from WA_Message wm  LEFT JOIN WA_Message_S wms on wm.id=wms.message where state=0 and wm.course is not null and student="+studentId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.uniqueResult();
		return (Integer) map.get("count");
	}
	
	public List<Map<String,Object>> getUnpushMessageList(){
		
		//时间区间
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		calendar.add(Calendar.MINUTE, -5);
		String startTime = sdf.format(calendar.getTime());  
		String endTime = sdf.format(new Date());
		
		String sql = "select * from WA_Message wm where wm.status=0 and wm.openTime>='"+startTime+"' and wm.openTime<'"+endTime+"'";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
}
