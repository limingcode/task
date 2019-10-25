package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.Hierarchy;
import com.util.HbmDAOUtil;

@Repository
public class HierarchyDAO extends HbmDAOUtil {

	/** 年级 */
	public List<Map<String, Object>> getGradeList() {
		String sql = "select distinct grade,(select name from Edu_Grade eg where eg.code=wh.grade ) name from WA_Hierarchy wh order by grade";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	/** 科目 */
	public List<Map<String, Object>> getSubjectList(String grade) {
		String sql = "select distinct wh.subject,(select name from Edu_Subject es where es.code=wh.subject ) name from WA_Hierarchy wh where wh.grade='"
				+ grade + "' order by wh.subject";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	/** 层次 */
	public List<Map<String, Object>> getCateList(String grade, String subject) {
		String sql = "select distinct wh.cate,(select name from Edu_Cate ec where ec.code=wh.cate ) name from WA_Hierarchy wh where wh.grade='"
				+ grade + "' and wh.subject='" + subject + "' order by wh.cate";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}

	/** 综合层次 */
	public Hierarchy getHierarchy(String grade, String subject, String cate) {
		String sql = "from Hierarchy where grade='" + grade + "' and subject='"
				+ subject + "' and cate='" + cate + "'";
		Query q = factory.getCurrentSession().createQuery(sql);
		return (Hierarchy) q.uniqueResult();
	}

	/** 综合层次 */
	public Hierarchy getHierarchy(int hierarchyId) {
		String sql = "from Hierarchy where id=" + hierarchyId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (Hierarchy) q.uniqueResult();
	}

	/** 综合层次 */
	public Map<String,Object> getHierarchyInfo(int hierarchyId) {
		String sql = "select wh.*,(select name from Edu_Cate ec where ec.code = wh.cate) cateName,(select name from Edu_Subject es where es.code = wh.subject) subjectName,(select name from Edu_Grade eg where eg.code = wh.grade) gradeName from WA_Hierarchy wh  where wh.id="
				+ hierarchyId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
}
