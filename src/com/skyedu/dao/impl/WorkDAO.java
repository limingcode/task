package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;
import com.util.HbmDAOUtil;

@Repository
@SuppressWarnings("unchecked")
public class WorkDAO extends HbmDAOUtil {

	/**
	 * 获取作业列表
	 * @param lessonId
	 * @param categoryId
	 * @return
	 */
	public List<Work> getWorkList(int lessonId, int categoryId) {
		String sql = "select w from Work w left outer join fetch w.workInfoList wi where DATALENGTH (wi.sortNo)>0 ";
		if (lessonId != 0) {
			sql = sql + " and lesson = " + lessonId;
		}
		if (categoryId != 0) {
			sql = sql + " and category = " + categoryId;
		}

		Query q = factory.getCurrentSession().createQuery(sql);
		return q.list();
	}

	/**
	 * 获取作业
	 * @param workId
	 * @return
	 */
	public Map<String, Object> getWork(int workId) {
		String sql = "select w.*,(select categoryName from WA_Category c where c.id=w.category)categoryName from WA_Work w where w.id=" + workId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
	
	/**
	 * 保存作业详细信息
	 * @param workInfo
	 */
	public void saveWorkInfo(WorkInfo workInfo) {
		factory.getCurrentSession().save(workInfo);
	}
	
	/**
	 * 获取作业列表
	 * @param lessonId
	 * @param categoryId
	 * @return
	 */
	public List<Map<String,Object>> getWorks(int lessonId, int categoryId){
		String sql = "select ww.*,(select username from Sys_User su where su.oaId=ww.oaId and su.oaId!=0) teacherName,(select categoryName from WA_Category wc where ww.category=wc.id)categoryName from WA_Work ww where ww.lesson="+lessonId;
		if (categoryId != 0) {
			sql = sql + " and category = " + categoryId;
		}
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	/**
	 * 获取作业详细列表
	 * @param workId
	 * @param flag
	 * @return
	 */
	public List<WorkInfo> getWorkInfoList(int workId, boolean flag){
		String sql ="select wi from WorkInfo wi where wi.work="+workId+" and DATALENGTH (wi.sortNo)>0 order by wi.sortNo";
		if (flag) {
			sql = "select wi from WorkInfo wi where wi.work="+workId+" order by wi.sortNo";
		}
		
		Query q = factory.getCurrentSession().createQuery(sql);
		return q.list();
	}
	
	/**
	 * 保存作业
	 * @param work
	 */
	public void saveWork(Work work){
		factory.getCurrentSession().save(work);
	}
	
	/**
	 * 删除作业
	 * @param workId
	 */
	public void delWork(int workId){
		String sql1 = "delete from WA_WorkInfo where work="+workId;
		SQLQuery q1 = factory.getCurrentSession().createSQLQuery(sql1);
		q1.executeUpdate();
		String sql2 = "delete from WA_Work where id="+workId;
		SQLQuery q2 = factory.getCurrentSession().createSQLQuery(sql2);
		q2.executeUpdate();
	}
	
	/**
	 * 删除作业详细
	 * @param workId
	 */
	public void delWorkInfo(int workId){
		String sql = "delete from WA_WorkInfo where work="+workId;
		SQLQuery q = factory.getCurrentSession().createSQLQuery(sql);
		q.executeUpdate();
	}
	
	/**
	 * 获取组卷详细
	 * @param workId
	 * @param questionId
	 * @return
	 */
	public WorkInfo getWorkInfo(int workId, int questionId){
		String sql = "from WorkInfo wf where wf.work="+workId+" and wf.question.iD="+questionId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (WorkInfo) q.uniqueResult();
	}
	
	public void updateWork(Work work){
		factory.getCurrentSession().update(work);
	}
	
	public Work getWorkBean(int workId){
		String sql = "from Work w where w.iD="+workId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (Work) q.uniqueResult();
	}
	
	public Map<String,Object> getWorkInfo(int workInfoId){
		String sql = "select * from WA_WorkInfo where id="+workInfoId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
	
	public List<Work> getWorksByQ(int questionId){
		String sql = "select distinct w from Work w left join fetch w.workInfoList wif left join fetch wif.question q where q.id="+questionId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return q.list();
	}
	
	public List<Map<String,Object>> getWorkInfoByQ(int questionId){
		String sql = "select * from WA_WorkInfo ww where ww.question="+questionId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	
	public List<Map<String, Object>> workCorrectStatistics(int type, String subject, String period, String grade, String gradeId) {
		if (type == 1) {
			StringBuilder sql = new StringBuilder();
			sql.append("select ltrim(rtrim(ec.grade)) grade, ec.cate, "); 
			sql.append(" 	case when avg(wr.correct) is null then '0' else Convert(decimal(3,2),avg(wr.correct)) end avgCorrect,");
			sql.append("    Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)) comple ");
			sql.append("from WA_ResultCard wr LEFT JOIN WA_Work ww on wr.work=ww.id "); 
			sql.append("		LEFT JOIN Edu_StudentClass esc on wr.studentId = esc.student "); 
			sql.append("		LEFT JOIN WA_Lesson wl on wl.id = ww.lesson "); 
			sql.append("        LEFT JOIN WA_Hierarchy wh on wl.hierarchy = wh.id "); 
			sql.append("        LEFT JOIN Edu_Course ec on esc.course = ec.id and wh.grade = ec.grade and wh.subject = ec.subject and wh.cate = ec.cate "); 
			sql.append("                 and charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',') > 0 "); 
			sql.append("where ec.cate in (select code from edu_cate ec where ec.code not in ('5','6','8','11','12') and id<1283)  and ec.name not like '测试%' "); 
			sql.append("	   and esc.status in (1,3) and ec.period in ("+ period +") and ec.subject = '"+ subject +"' "); 
			sql.append("group by ec.grade,ec.cate "); 
			sql.append("order by ec.cate,ec.grade");
			return factory.getCurrentSession().createSQLQuery(sql.toString())
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} else if (type == 2) {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ("); 
			sql.append("        select ec.id, ec.name, '0' category, '综合' categoryName,"); 
			sql.append("                (select name from edu_grade where ec.grade = code) grade, "); 
			sql.append("                (select name from edu_cate where ec.cate = code) cate, "); 
			sql.append("                case when avg(wr.correct) is null then '0' else Convert(decimal(3,2),avg(wr.correct)) end avgCorrect, ");
			sql.append("  				Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)) comple ");
			sql.append("        from WA_ResultCard wr LEFT JOIN WA_Work ww on wr.work=ww.id "); 
			sql.append("                left join Edu_StudentClass esc on wr.studentId = esc.student "); 
			sql.append("                left join WA_Lesson wl on wl.id = ww.lesson "); 
			sql.append("                left join WA_Hierarchy wh on wl.hierarchy = wh.id "); 
			sql.append("                left join Edu_Course ec on esc.course = ec.id and wh.grade = ec.grade and wh.subject = ec.subject and wh.cate = ec.cate "); 
			sql.append("                           and charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',') > 0 "); 
			sql.append("        where esc.status in (1,3) and ec.cate in (select code from edu_cate ec where ec.code not in ('5','6','8','11','12') and id<1283) and ec.name not like '测试%' "); 
			sql.append("                and ec.period in ("+ period +") and ec.grade = '"+ grade +"' and ec.subject = '"+ subject +"' ");  
			sql.append("        group by ec.id, ec.name, ec.grade, ec.cate "); 
			sql.append("    union all    "); 
			sql.append("        select ec.id, ec.name,ww.category,"); 
			sql.append("                (select categoryName from wa_category where ww.category = id) categoryName, "); 
			sql.append("                (select name from edu_grade where ec.grade = code) grade, "); 
			sql.append("                (select name from edu_cate where ec.cate = code) cate, "); 
			sql.append("                case when avg(wr.correct) is null then '0' else Convert(decimal(3,2),avg(wr.correct)) end avgCorrect, "); 
			sql.append("                Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)) comple ");
			sql.append("        from WA_ResultCard wr LEFT JOIN WA_Work ww on wr.work=ww.id "); 
			sql.append("                left join Edu_StudentClass esc on wr.studentId = esc.student "); 
			sql.append("                left join WA_Lesson wl on wl.id = ww.lesson "); 
			sql.append("                left join WA_Hierarchy wh on wl.hierarchy = wh.id "); 
			sql.append("                left join Edu_Course ec on esc.course = ec.id and wh.grade = ec.grade and wh.subject = ec.subject and wh.cate = ec.cate "); 
			sql.append("                           and charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',') > 0 "); 
			sql.append("        where esc.status in (1,3) and ec.cate in (select code from edu_cate ec where ec.code not in ('5','6','8','11','12') and id<1283) and ec.name not like '测试%' "); 
			sql.append("                and ec.period in ("+ period +") and ec.grade = '"+ grade +"' and ec.subject = '"+ subject +"' "); 
			sql.append("        group by ec.id, ec.name, ec.grade, ec.cate , ww.category "); 
			sql.append(") a order by a.cate, a.name, a.category");
			return factory.getCurrentSession().createSQLQuery(sql.toString())
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} else {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ( "); 
			sql.append("        select wl.id,wl.sortNo, '0' category, '综合' categoryName, "); 
			sql.append("                case when max(wr.correct) is null then '0' else Convert(decimal(3,2),max(wr.correct)) end maxCorrect, "); 
			sql.append("                case when min(wr.correct) is null then '0' else Convert(decimal(3,2),min(wr.correct)) end minCorrect, "); 
			sql.append("                case when avg(wr.correct) is null then '0' else Convert(decimal(3,2),avg(wr.correct)) end avgCorrect, ");
			sql.append("				Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)) comple ");
			sql.append("        from Edu_Course ec "); 
			sql.append("                left join WA_hierarchy wh on wh.grade = ec.grade and wh.subject = ec.subject and wh.cate = ec.cate "); 
			sql.append("                left join WA_Lesson wl on wl.hierarchy = wh.id and charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',') > 0 "); 
			sql.append("                left join Edu_studentClass esc on ec.id = esc.course "); 
			sql.append("                left join WA_Work ww on wl.id = ww.lesson "); 
			sql.append("                left join WA_ResultCard wr on wr.work = ww.id and wr.studentId= esc.student "); 
			sql.append("        where esc.status in (1,3) and ec.id = "+ gradeId +" "); 
			sql.append("        group by wl.id,wl.sortNo   "); 
			sql.append("    union all "); 
			sql.append("        select wl.id, wl.sortNo, ww.category, "); 
			sql.append("                (select categoryName from wa_category where ww.Category = id) categoryName, "); 
			sql.append("                case when max(wr.correct) is null then '0' else Convert(decimal(3,2),max(wr.correct)) end maxCorrect, "); 
			sql.append("                case when min(wr.correct) is null then '0' else Convert(decimal(3,2),min(wr.correct)) end minCorrect, "); 
			sql.append("                case when avg(wr.correct) is null then '0' else Convert(decimal(3,2),avg(wr.correct)) end avgCorrect, ");
			sql.append("				Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)) comple ");
			sql.append("        from Edu_Course ec"); 
			sql.append("                left join WA_hierarchy wh on wh.grade = ec.grade and wh.subject = ec.subject and wh.cate = ec.cate "); 
			sql.append("                left join WA_Lesson wl on wl.hierarchy = wh.id and charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',') > 0 "); 
			sql.append("                left join Edu_studentClass esc on ec.id = esc.course "); 
			sql.append("                left join WA_Work ww on wl.id = ww.lesson "); 
			sql.append("                left join WA_ResultCard wr on wr.work = ww.id and wr.studentId= esc.student "); 
			sql.append("        where esc.status in (1,3) and ec.id = "+ gradeId +" "); 
			sql.append("        group by ww.category, wl.id,wl.sortNo"); 
			sql.append(") a order by a.sortNo, a.category");

			return factory.getCurrentSession().createSQLQuery(sql.toString())
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
	}

	public List<Map<String, Object>> studentWorkCorrectStatis(int courseId, String studentId) {
		if (StringUtils.isEmpty(studentId)) {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ("); 
			sql.append("        select wr.studentId, '0' category, '综合' categoryName,"); 
			sql.append("                (select name from edu_student where id = wr.studentid) name, "); 
			sql.append("                case when avg(wr.correct) is null then '0' else Convert(decimal(5,4),avg(wr.correct)) end avgCorrect, "); 
			sql.append("				Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)) comple ");
			sql.append("        from WA_ResultCard wr left join WA_Work ww on wr.work=ww.id "); 
			sql.append("                left join Edu_StudentClass esc on wr.studentId = esc.student "); 
			sql.append("                left join Edu_Course ec on esc.course=ec.id "); 
			sql.append("        where esc.status=1 and ec.id = "+ courseId +" "); 
			sql.append("        group by wr.studentId "); 
			sql.append("    union all   "); 
			sql.append("        select wr.studentId, ww.category, (select categoryName from wa_category where ww.Category = id) categoryName, "); 
			sql.append("                (select name from edu_student where id = wr.studentid) name,"); 
			sql.append("                case when avg(wr.correct) is null then '0' else Convert(decimal(5,4),avg(wr.correct)) end avgCorrect, "); 
			sql.append("				Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)) comple ");
			sql.append("        from WA_ResultCard wr left join WA_Work ww on wr.work=ww.id "); 
			sql.append("                left join Edu_StudentClass esc on wr.studentId = esc.student "); 
			sql.append("                left join Edu_Course ec on esc.course=ec.id "); 
			sql.append("        where esc.status in (1,3) and ec.id = "+ courseId +" "); 
			sql.append("        group by wr.studentId, ww.category "); 
			sql.append(") a order by a.studentId, a.category ");
			return factory.getCurrentSession().createSQLQuery(sql.toString())
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ("); 
		sql.append("        select es.id studentId, wl.id lesson, wl.sortNo, "); 
		sql.append("               '0' category, '综合' categoryName, es.name, "); 
		sql.append("               case when avg(wr.correct) is null then '0' else Convert(decimal(5,4),avg(wr.correct)) end correct, "); 
		sql.append("			   isnull(Convert(decimal(3,2),sum(wr.hasDealed)/(count(wr.hasDealed)+0.0)), 0) comple ");
		sql.append("        from  WA_lesson wl inner join WA_Hierarchy wh on wl.hierarchy = wh.id "); 
		sql.append("              inner join Edu_Course ec on ec.grade = wh.grade and ec.subject = wh.subject and ec.cate = wh.cate "); 
		sql.append("              and charindex(','+CONVERT(varchar(10),ec.period)+',', ','+wl.period+',')>0 "); 
		sql.append("              left join WA_category wc on wc.subject = ec.subject "); 
		sql.append("              left join WA_Work ww on ww.lesson = wl.id and ww.category = wc.id "); 
		sql.append("              left join WA_ResultCard wr on wr.work=ww.id and wr.studentid = "+ studentId); 
		sql.append("              left join Edu_Student es on es.id = "+ studentId); 
		sql.append("        where ec.id = "+ courseId); 
		sql.append("        group by wl.id, wl.sortNo, es.id, es.name"); 
		sql.append("    union all"); 
		sql.append("        select es.id studentId, wl.id lesson, wl.sortNo, "); 
		sql.append("               wc.id category, wc.categoryName, es.name, "); 
		sql.append("               case when wr.correct is null then 0 else wr.correct end correct,"); 
		sql.append("			   isnull(wr.hasDealed, 0) comple ");
		sql.append("        from  WA_lesson wl inner join WA_Hierarchy wh on wl.hierarchy = wh.id "); 
		sql.append("              inner join Edu_Course ec on ec.grade = wh.grade and ec.subject = wh.subject and ec.cate = wh.cate "); 
		sql.append("              and charindex(','+CONVERT(varchar(10),ec.period)+',', ','+wl.period+',')>0 "); 
		sql.append("              left join WA_category wc on wc.subject = ec.subject "); 
		sql.append("              left join WA_Work ww on ww.lesson = wl.id and ww.category = wc.id "); 
		sql.append("              left join WA_ResultCard wr on wr.work=ww.id and wr.studentid = "+ studentId); 
		sql.append("              left join Edu_Student es on es.id = "+ studentId); 
		sql.append("        where ec.id = "+ courseId); 
		sql.append(") a order by a.lesson, a.category");
		return factory.getCurrentSession().createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
}
