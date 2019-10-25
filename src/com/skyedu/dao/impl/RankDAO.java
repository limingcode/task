package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.util.CommonUtil;
import com.util.DateUtil;
import com.util.HbmDAOUtil;

@Repository
public class RankDAO extends HbmDAOUtil {

	public int getRankCount(Map<String,Object> condition){
		String grade = (String) condition.get("grade");
		String cate = (String) condition.get("cate");
		String subject = (String) condition.get("subject");
		String sql = "";
		Object dept = condition.get("dept");
		String weekStartTime = DateUtil.getWeekStartTime(0);
		String weekEndTime = DateUtil.getWeekEndTime(0);
		if (dept!=null) {
			sql = "select count(0) count from WA_Rank wr where wr.dept='"+(String)dept+"' and wr.cate='"+cate+"' and wr.grade='"+grade+"' and wr.subject='"+subject+"' and wr.createDate>='"+weekStartTime+"' and wr.createDate<'"+weekEndTime+"'";
		}else{
			sql = "select count(0) count from WA_Rank wr where wr.dept is null and wr.cate='"+cate+"' and wr.grade='"+grade+"' and wr.subject='"+subject+"' and wr.createDate>='"+weekStartTime+"' and wr.createDate<'"+weekEndTime+"'";
		}
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.uniqueResult();
		return (Integer) map.get("count");
	}
	
	public void setDesigntion(Map<String,Object> condition){
		String grade = (String) condition.get("grade");
		String cate = (String) condition.get("cate");
		String subject = (String) condition.get("subject");
		String designtion = (String) condition.get("designtion");
		long start = (Long) condition.get("start");
		long end = (Long) condition.get("end");
		String sql = "";
		Object dept = condition.get("dept");
		String weekStartTime = DateUtil.getWeekStartTime(0);
		String weekEndTime = DateUtil.getWeekEndTime(0);
		if (dept!=null) {
			sql = "update WA_Rank set designation='"+designtion+"' where dept='"+(String)dept+"' and cate='"+cate+"' and grade='"+grade+"' and subject='"+subject+"' and rank>"+start+" and rank<="+end+" and createDate>='"+weekStartTime+"' and createDate<'"+weekEndTime+"'";
		}else{
			sql = "update WA_Rank set designation='"+designtion+"' where dept is null and cate='"+cate+"' and grade='"+grade+"' and subject='"+subject+"' and rank>"+start+" and rank<="+end+" and createDate>='"+weekStartTime+"' and createDate<'"+weekEndTime+"'";
		}
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public List<Map<String,Object>> getStudentList(Map<String,Object> condition){
		String grade = (String) condition.get("grade");
		String subject = (String) condition.get("subject");
		int week = (Integer) condition.get("week");
		String weekStartTime = DateUtil.getWeekStartTime(week);
		String weekEndTime = DateUtil.getWeekEndTime(week);
		String sql = "select wr.*,ec.name cateName,esu.name subjectName,eg.name gradeName from WA_Rank wr LEFT JOIN Edu_Student es on es.id=wr.studentId LEFT JOIN Edu_Cate ec on ec.code=wr.cate left join Edu_Subject esu on esu.code=wr.subject left join Edu_Grade eg on eg.code=wr.grade where wr.designation is not null and wr.subject='"+subject+"' and wr.grade='"+grade+"' and createDate>='"+weekStartTime+"' and createDate<'"+weekEndTime+"'";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public Map<String,Object> getStudent (Map<String,Object> condition){
		String grade = (String) condition.get("grade");
		String cate = (String) condition.get("cate");
		String subject = (String) condition.get("subject");
		int week = (Integer) condition.get("week");
		int studentId = (Integer) condition.get("studentId");
		String weekStartTime = DateUtil.getWeekStartTime(week);
		String weekEndTime = DateUtil.getWeekEndTime(week);
		String sql = "select wr.*,es.img img,es.name studentName,es.sex sex,ec.name cateName,esu.name subjectName,eg.name gradeName from WA_Rank wr LEFT JOIN Edu_Student es on es.id=wr.studentId LEFT JOIN Edu_Cate ec on ec.code=wr.cate left join Edu_Subject esu on esu.code=wr.subject left join Edu_Grade eg on eg.code=wr.grade where wr.studentId='"+studentId+"' and wr.subject='"+subject+"' and wr.grade='"+grade+"' and wr.cate='"+cate+"' and createDate>='"+weekStartTime+"' and createDate<'"+weekEndTime+"'";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
	
	public List<Map<String,Object>> getRankStudentList(Map<String,Object> condition){
		String grade = (String) condition.get("grade");
		String subject = (String) condition.get("subject");
		String cate = (String) condition.get("cate");
		int week = (Integer) condition.get("week");
		int pageNo = (Integer) condition.get("pageNo");
		int pageSize = CommonUtil.RANKSIZE;
		Object object = condition.get("dept");
		String weekStartTime = DateUtil.getWeekStartTime(week);
		String weekEndTime = DateUtil.getWeekEndTime(week);
		String weekStartTimeO = DateUtil.getWeekStartTime(week-1);
		String weekEndTimeO = DateUtil.getWeekEndTime(week-1);
		String sql = "";
		if (object==null) {
			sql = "select wr.*,es.img img,es.name studentName,es.sex sex,ec.name cateName,esu.name subjectName,eg.name gradeName,(select top 1 wro.rank from WA_Rank wro where wro.studentId=wr.studentId and wro.cate='"+cate+"' and wro.subject='"+subject+"' and wro.grade='"+grade+"' and wro.dept is null and createDate>='"+weekStartTimeO+"' and createDate<'"+weekEndTimeO+"')oldRank from WA_Rank wr LEFT JOIN Edu_Student es on es.id=wr.studentId LEFT JOIN Edu_Cate ec on ec.code=wr.cate left join Edu_Subject esu on esu.code=wr.subject left join Edu_Grade eg on eg.code=wr.grade where wr.cate='"+cate+"' and wr.subject='"+subject+"' and wr.grade='"+grade+"' and wr.dept is null and createDate>='"+weekStartTime+"' and createDate<'"+weekEndTime+"' order by wr.rank";
		}else{
			String dept = (String) object;
			sql = "select wr.*,es.img img,es.name studentName,es.sex sex,ec.name cateName,esu.name subjectName,eg.name gradeName,(select top 1 wro.rank from WA_Rank wro where wro.studentId=wr.studentId and wro.cate='"+cate+"' and wro.subject='"+subject+"' and wro.grade='"+grade+"' and wro.dept='"+dept+"' and createDate>='"+weekStartTimeO+"' and createDate<'"+weekEndTimeO+"')oldRank from WA_Rank wr LEFT JOIN Edu_Student es on es.id=wr.studentId LEFT JOIN Edu_Cate ec on ec.code=wr.cate left join Edu_Subject esu on esu.code=wr.subject left join Edu_Grade eg on eg.code=wr.grade where wr.cate='"+cate+"' and wr.subject='"+subject+"' and wr.grade='"+grade+"' and wr.dept='"+dept+"' and createDate>='"+weekStartTime+"' and createDate<'"+weekEndTime+"' order by wr.rank";
		}
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize);;
		return q.list();
	}
	
	public List<Map<String,Object>> getDesignationList(int studentId){
		String weekStartTime = DateUtil.getWeekStartTime(-1);
		String weekEndTime = DateUtil.getWeekEndTime(-1);
		String sql = "select designation from Wa_Rank wr where designation is not null and createDate>='"+weekStartTime+"' and createDate<'"+weekEndTime+"' and studentId="+studentId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
}
