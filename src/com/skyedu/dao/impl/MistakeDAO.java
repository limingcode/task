package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.skyedu.model.Mistake;
import com.util.CommonUtil;
import com.util.HbmDAOUtil;

@Repository
public class MistakeDAO extends HbmDAOUtil {

	public void saveMistakes(Mistake mistake) {
		factory.getCurrentSession().save(mistake);
	}
	
	public void updateMistake(Mistake mistake){
		factory.getCurrentSession().update(mistake);
	}

	/*
	 * public List<Map<String,Object>> getMistakeList(Map<String,Object>
	 * condition){ int studentId = (Integer) condition.get("studentId"); int
	 * courseId = (Integer) condition.get("courseId"); int pageNo = (Integer)
	 * condition.get("pageNo"); String sql =
	 * "select wm.id mistakeId,wm.workInfo workInfoId,wl.id lessonId,wl.sortNo lessonSortNo,wc.categoryName categoryName,wq.*,wq.id questionId from WA_Mistakes wm LEFT JOIN WA_WorkInfo wwi on wm.workInfo=wwi.id LEFT JOIN WA_Work ww on ww.id=wwi.work left join WA_Category wc on wc.id=ww.category left join WA_Lesson wl on wl.id =ww.lesson LEFT JOIN WA_Hierarchy wh on wl.hierarchy=wh.id LEFT JOIN Edu_Course ec"
	 * +
	 * " on ec.grade=wh.grade and ec.cate=wh.cate and ec.subject=wh.subject and charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',')>0 LEFT JOIN WA_Question wq on wq.id=wwi.question where ec.id="
	 * +courseId+" and wm.studentId="+studentId+" order by wm.id desc"; Query q
	 * = factory.getCurrentSession().createSQLQuery(sql)
	 * .setResultTransformer(Transformers
	 * .ALIAS_TO_ENTITY_MAP).setFirstResult((pageNo
	 * -1)*CommonUtil.MISTAKESIZE).setMaxResults(CommonUtil.MISTAKESIZE);
	 * 
	 * return q.list(); }
	 */

	public List<Map<String, Object>> getMistakeList(
			Map<String, Object> condition) {
		int studentId = (Integer) condition.get("studentId");
		int courseId = (Integer) condition.get("courseId");
		int pageNo = (Integer) condition.get("pageNo");
		String sql = "select wm.id mistakeId,wrc.id resultCardInfoId,wq.brief,wrc.modifyDate,wl.sortNo lessonSortNo,wc.categoryName,wwi.sortNo workSortNo from WA_Mistakes wm LEFT JOIN WA_ResultCardInfo wrc on wrc.resultCard=wm.resultCard and wm.workInfo=wrc.workInfo LEFT JOIN WA_WorkInfo wwi on wm.workInfo=wwi.id LEFT JOIN WA_Work ww on ww.id=wwi.work left join WA_category wc on ww.category=wc.id left join WA_Lesson wl on wl.id =ww.lesson LEFT JOIN WA_Hierarchy wh on wl.hierarchy=wh.id LEFT JOIN Edu_Course ec on ec.grade=wh.grade and ec.cate=wh.cate and ec.subject=wh.subject and charindex(','+CONVERT(varchar(10),ec.period)+',',','+wl.period+',')>0 LEFT JOIN WA_Question wq on wq.id=wwi.question where ec.id="
				+ courseId
				+ " and wm.studentId="
				+ studentId
				+ " order by wl.sortNo desc,wc.categoryName,wwi.sortNo";
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult((pageNo - 1) * CommonUtil.MISTAKESIZE)
				.setMaxResults(CommonUtil.MISTAKESIZE);

		return q.list();
	}

	/** 删除错题集详细 */
	public void delMistake(int resultCardId) {
		String sql = "delete from WA_Mistakes where resultCard=" + resultCardId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	public void removeMistakes(Mistake mistake) {
		factory.getCurrentSession().delete(mistake);
	}

	public void delMistakeByWork(int workId) {
		String sql = "delete wm from WA_Mistakes wm left join WA_ResultCard wr on wr.id=wm.resultCard where wr.work="
				+ workId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
}
