package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.util.HbmDAOUtil;

@Repository
public class AnalyzationDAO extends HbmDAOUtil {

	public List<Map<String,Object>> getRankList(String cate,String subject,String grade ,int studentId){
		String sql = "select id iD,* from WA_rank where cate='"+cate+"' and subject='"+subject+"' and grade='"+grade+"' and studentId="+studentId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public List<Map<String,Object>> getCorrectList(String cate,String subject,String grade ,int studentId){
		String sql = "select wl.sortNo,isnull(Convert(decimal(5,2),avg(wr.correct)),-1) correct from wa_lesson wl LEFT JOIN WA_Work ww on ww.lesson=wl.id LEFT JOIN WA_ResultCard wr on wr.work=ww.id and wr.studentId="+studentId+" LEFT JOIN WA_Hierarchy wh on wl.hierarchy=wh.id where wh.cate='"+cate+"' and wh.grade='"+grade+"' and wh.subject='"+subject+"' group by wl.sortNo order by wl.sortNo";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public Map<String,Object> getNearlyCorrect(int category,String cate,String subject,String grade ,int studentId){
		String sql = "select Convert(decimal(5,2),wr.correct) correct,Convert(decimal(5,2),wr.score) score,wl.sortNo from WA_ResultCard wr LEFT JOIN WA_Work ww on ww.id=wr.work LEFT JOIN WA_Lesson wl on ww.lesson=wl.id LEFT JOIN WA_Hierarchy wh on wh.id=wl.hierarchy where wr.hasDealed=1 and ww.category="+category+" and wr.studentId="+studentId+" and wh.cate='"+cate+"' and wh.subject='"+subject+"' and wh.grade='"+grade+"' ORDER BY wr.modifyDate desc";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = q.list();
		if (list!=null && list.size()>0) {
			return (Map<String, Object>) list.get(0);
		}else {
			return null;
		}
	}
}