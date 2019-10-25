package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.skyedu.model.Question;
import com.util.CommonUtil;
import com.util.HbmDAOUtil;

@Repository
public class QuestionDAO extends HbmDAOUtil {

	/**
	 * 获取题目
	 * 
	 * @param id
	 * @return
	 */
	public Question getQuestion(int id) {
		String sql = "from Question q where q.iD = " + id;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (Question) q.uniqueResult();
	}

	public Map<String, Object> getQuesMap(int id) {
		String sql = "select * from WA_Question where id = " + id;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}

	/**
	 * 保存题目
	 * 
	 * @param question
	 */
	public void saveQuestion(Question question) {
		factory.getCurrentSession().save(question);
	}
	
	/**
	 * 更新题目
	 * 
	 * @param question
	 */
	public void updateQuestion(Question question) {
		factory.getCurrentSession().update(question);
	}

	/**
	 * 题目列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Question> quesList(Map<String, Object> condition) {

		String grade = (String) condition.get("grade");
		String subject = (String) condition.get("subject");
		String cate = (String) condition.get("cate");
		int pageNo = (Integer) condition.get("pageNo");
		int pageSize = (Integer) condition.get("pageSize");
		String fsql = "select q from Question q left outer join fetch q.hierarchy h where q.parentQuestion is null ";
		String fcon = "";
		if (!StringUtils.isEmpty(grade)) {
			fcon = fcon + " and h.grade='" + grade + "'";
		}
		if (!StringUtils.isEmpty(subject)) {
			fcon = fcon + " and h.subject='" + subject + "'";
		}
		if (!StringUtils.isEmpty(cate)) {
			fcon = fcon + " and h.cate='" + cate + "'";
		}
		String sql = fsql + fcon + " order by q.createDate";
		Query q = factory.getCurrentSession().createQuery(sql)
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize);
		return q.list();
	}

	/**
	 * 题目列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> questionList(Map<String, Object> condition) {

		String title = (String) condition.get("title");
		String grade = (String) condition.get("grade");
		String subject = (String) condition.get("subject");
		String cate = (String) condition.get("cate");
		int pageNo = (Integer) condition.get("pageNo");
		String fsql = "select wq.*,(select userName from Sys_User su where su.oaId=wq.oaId and su.oaId!=0) teacherName,(select userName from Sys_User su where su.oaId=wq.editor and su.oaId!=0) editorName,(select name from edu_cate ec where ec.code=wh.cate)cateName,(select name from edu_subject es where es.code=wh.subject)subjectName,(select name from edu_grade eg where eg.code=wh.grade)gradeName from WA_Question wq left join WA_Hierarchy wh on wq.hierarchy = wh.id where wq.pId is null ";
		String fcon = "";
		if (!StringUtils.isEmpty(title)) {
			fcon = fcon + " and wq.brief like '%" + title + "%'";
		}
		if (!StringUtils.isEmpty(grade)) {
			fcon = fcon + " and wh.grade='" + grade + "'";
		}
		if (!StringUtils.isEmpty(subject)) {
			fcon = fcon + " and wh.subject='" + subject + "'";
		}
		if (!StringUtils.isEmpty(cate)) {
			fcon = fcon + " and wh.cate='" + cate + "'";
		}
		
		String sql = fsql + fcon + " order by wq.createDate desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setFirstResult((pageNo - 1) * CommonUtil.QUESTIONSIZE)
				.setMaxResults(CommonUtil.QUESTIONSIZE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		String sql1 = "select count(1)count from WA_Question wq left join WA_Hierarchy wh on wq.hierarchy = wh.id where wq.pId is null "+ fcon;
		Query q1 = factory.getCurrentSession().createSQLQuery(
				sql1).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		int size = (Integer) ((Map<String,Object>)q1.uniqueResult()).get("count");
		int totalPage = size / CommonUtil.QUESTIONSIZE + 1;
		if (size % CommonUtil.QUESTIONSIZE == 0) {
			totalPage = totalPage - 1;
		}
		if (pageNo > totalPage) {
			pageNo = totalPage;
			condition.put("pageNo", pageNo);
		}
		condition.put("totalPage", totalPage);
		return q.list();
	}

	/**
	 * 获取子题列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Map<String, Object>> childList(int parentId) {
		String sql = "select wq.*,(select name from Edu_Teacher et where et.oaId=wq.oaId) teacherName from WA_Question wq left join WA_Hierarchy wh on wq.hierarchy = wh.id where wq.pId = "
				+ parentId + " order by wq.id";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	/**
	 * 获取题目列表以及相关作业排序
	 * 
	 * @param workId
	 * @return
	 */
	public List<Map<String, Object>> getQuestionByWork(int workId,String brief) {

		String sql = "";
		if (StringUtils.isEmpty(brief)) {
			sql = "select wq.*,(select max(sortNo)sortNo from WA_WorkInfo wwi LEFT JOIN WA_Work ww on wwi.work=ww.id where wwi.question=wq.id and (ww.id="
					+ workId
					+ " or ww.id is null))sortNo,(select max(userName)username from Sys_User su where su.oaId=wq.oaId and su.oaId!=0)teacherName from WA_Question wq where wq.pId is null and wq.hierarchy=(select hierarchy from WA_Work ww LEFT JOIN WA_Lesson wl on wl.id=ww.lesson where ww.id="
					+ workId + ") order by wq.questionType,wq.id desc";
		}else{
			sql = "select wq.*,(select max(sortNo)sortNo from WA_WorkInfo wwi LEFT JOIN WA_Work ww on wwi.work=ww.id where wwi.question=wq.id and (ww.id="
					+ workId
					+ " or ww.id is null))sortNo,(select max(userName)username from Sys_User su where su.oaId=wq.oaId and su.oaId!=0)teacherName from WA_Question wq where wq.pId is null and wq.hierarchy=(select hierarchy from WA_Work ww LEFT JOIN WA_Lesson wl on wl.id=ww.lesson where ww.id="
					+ workId + ") and wq.brief like '%"+brief+ "%' order by wq.questionType,wq.id desc";
		}
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	public List<Map<String, Object>> getResultList(int questionId) {
		String sql = "select * from WA_Result where question=" + questionId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	public void saveQuestionHtml(int questionId, String qItemCnt) {
		String sql = "insert into WA_QuestionHtml (questionId,qItemCnt) values (:questionId,:qItemCnt)";
		SQLQuery q = factory.getCurrentSession().createSQLQuery(sql);
		q.setParameter("questionId", questionId);
		q.setParameter("qItemCnt", qItemCnt);
		q.executeUpdate();
	}

	public void updateQuestionHtml(int questionId, String qItemCnt) {
		String sql = "update WA_QuestionHtml set qItemCnt=:qItemCnt where questionId=" + questionId;
		SQLQuery q = factory.getCurrentSession().createSQLQuery(sql);
		q.setParameter("qItemCnt", qItemCnt);
		q.executeUpdate();
	}

	public Map<String, Object> getQuestionHtml(int questionId) {
		String sql = "select * from WA_QuestionHtml where questionId="+questionId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
	
	public void deleteResults(int questionId){
		String sql = "delete from WA_Result where question=" + questionId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void deleteQuestionHtml(int questionId){
		String sql1 = "delete from WA_QuestionHtml where questionId=" + questionId;
		factory.getCurrentSession().createSQLQuery(sql1).executeUpdate();
	}
}
