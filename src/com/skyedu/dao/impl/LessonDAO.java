package com.skyedu.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.skyedu.model.LessonBean;
import com.util.DateUtil;
import com.util.HbmDAOUtil;

@Repository
public class LessonDAO extends HbmDAOUtil {

	/**
	 * 获取该学期该层次下的课次列表
	 * 
	 * @param period
	 * @param grade
	 * @param subject
	 * @param cate
	 * @return
	 */
	public List<Map<String, Object>> getLessonList(String period, String grade,
			String subject, String cate) {
		String sql = "select wl.id iD,wl.sortNo,wl.openTime,wl.modifyDate,wl.createDate,wl.hierarchy,wl.brief from WA_Lesson wl where charindex(',"+period+",',','+wl.period+',')>0 ";
		if (!StringUtils.isEmpty(grade) && !StringUtils.isEmpty(subject)
				&& !StringUtils.isEmpty(cate)) {
			sql = sql
					+ " and wl.hierarchy = (select id from WA_Hierarchy wh where wh.grade='"
					+ grade + "' and wh.subject='" + subject
					+ "' and wh.cate='" + cate + "')";
		}
		sql = sql + " order by wl.hierarchy, wl.sortNo ";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	/**
	 * 获取课次的详细信息
	 * 
	 * @param lessonId
	 * @return
	 */
	public Map<String, Object> getLessonInfo(int lessonId) {
		String sql = "select wl.*,t.name periodName,t.year,wh.cate cate,wh.subject subject,wh.grade grade,(select name from Edu_Cate ec where ec.code=wh.cate ) cateName,(select name from Edu_Subject es where es.code=wh.subject )subjectName,(select name from Edu_Grade eg where eg.code=wh.grade ) gradeName from WA_Lesson wl "
				+ "LEFT JOIN WA_Hierarchy wh "
				+ "on wh.id = wl.hierarchy "
				+ "LEFT JOIN (select p.year,p.pName name,stuff((select ','+cast(epe.id AS varchar(10)) from (select *,left(name,2) pName from Edu_Period ep ) epe where epe.year=p.year and epe.pName=p.pName for xml path('')),1,1,'') id from (select *,left(name,2) pName from Edu_Period ep where ep.status!=-1) p group by pName,p.year)t "
				+ "on t.id like '%'+wl.period+'%' where wl.id="
				+ lessonId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
	
	/**
	 * 获取课次的详细信息
	 * 
	 * @param lessonId
	 * @return
	 */
	public LessonBean getLessonBean(int lessonId) {
		String sql = "from LessonBean where iD="+ lessonId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (LessonBean) q.uniqueResult();
	}
	
	public List<LessonBean> getLessonBeanList(String period, String grade,
			String subject, String cate,int studentId) {
		String sql = "from LessonBean a left join a.hierarchy b left join  a.workList c , c.resultCardList.elements d where charindex(',"+period+",',','+a.period+',')>0 and b.grade='"+grade+"' and b.cate="+cate+" and b.subject='"+subject+"' and d.studentId="+studentId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return q.list();
	}

	/**
	 * 添加一个课次
	 * 
	 * @param lesson
	 */
	public void commitLesson(Map<String, Object> lesson) {
		String sql = "insert into WA_Lesson (sortNo,hierarchy,period,openTime,modifyDate,createDate,brief) values ("
				+ lesson.get("sortNo")
				+ ","
				+ lesson.get("hierarchy")
				+ ",'"
				+ lesson.get("period")
				+ "','"
				+ lesson.get("openTime")
				+ "','"
				+ lesson.get("modifyDate")
				+ "','"
				+ lesson.get("createDate")
				+ "','"
				+ lesson.get("brief")
				+ "')";
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		q.executeUpdate();
	}

	/**
	 * 删除该学期该层次的最大课次
	 * 
	 * @param hierarchyId
	 * @param periodId
	 */
	public void deleteMaxLesson(int lessonId) {
		String sql = "delete from WA_Lesson where id="+lessonId;
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		q.executeUpdate();
	}

	/**
	 * 该学期该层次的最大课次
	 * 
	 * @param hierarchyId
	 * @param periodId
	 */
	public Map<String, Object> getMaxLesson(int hierarchyId, String periodId) {
		String sql = "select top 1 wl.*,t.name periodName,t.year from WA_Lesson wl left join (select p.year,p.pName name,stuff((select ','+cast(epe.id AS varchar(10)) from (select *,left(name,2) pName from Edu_Period ep ) epe where epe.year=p.year and epe.pName=p.pName for xml path('')),1,1,'') id from (select *,left(name,2) pName from Edu_Period ep where ep.status!=-1) p group by pName,p.year)t on t.id like '%'+wl.period+'%' where hierarchy="+hierarchyId+" and period='"+periodId+"' ORDER BY id desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}

	public void updateLesson(int lessonId) {
		String sql = "update WA_Lesson set modifyDate='" + DateUtil.getNow()
				+ "' where id=" + lessonId;
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		q.executeUpdate();
	}

	public void updateOpenTime(int lessonId, String openTime) {
		String sql = "update WA_Lesson set openTime='" + openTime
				+ "' where id=" + lessonId;
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		q.executeUpdate();
	}
	
	public void updateBrief(int lessonId, String brief) {
		String sql = "update WA_Lesson set brief='" + brief
				+ "' where id=" + lessonId;
		Query q = factory.getCurrentSession().createSQLQuery(sql);
		q.executeUpdate();
	}

	public List<Map<String, Object>> getLessonListByCourse(int courseId) {
		String sql = "SELECT wl.*,wh.cate cate,wh.subject subject,wh.grade grade,ect.name cateName,es.name subjectName,eg.name gradeName from WA_Lesson wl "
				+ " left join WA_Hierarchy wh on wl.hierarchy = wh.id"
				+ " left join Edu_Course ec on wh.cate=ec.cate AND wh.grade=ec.grade and wh.subject=ec.subject and  charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',')>0"
				+ " left join Edu_Cate ect on ect.code=wh.cate"
				+ " left join Edu_Subject es on es.code=wh.subject"
				+ " left join Edu_Grade eg on eg.code=wh.grade"
				+ " where ec.id="+courseId+" order by wl.sortNo";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
}
