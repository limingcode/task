package com.skyedu.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.skyedu.model.Mistake;
import com.skyedu.model.ResultCard;
import com.skyedu.model.ResultCardInfo;
import com.util.DateUtil;
import com.util.HbmDAOUtil;

@Repository
public class ResultCardDAO extends HbmDAOUtil {

	/** 获取答题卡列表 */
	public List<ResultCard> getResultCardList(int lessonId, int studentId) {
		String sql = "select distinct rd from ResultCard rd left join rd.resultCardInfoList rdi left "
				+ " join rdi.workInfo wi left join wi.work w left join w.lesson l where rd.openTime<=getdate() and DATALENGTH (wi.appSortNo)>0 and rd.studentId="
				+ studentId + " and l.iD=" + lessonId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return q.list();
	}
	
	/** 获取答题卡列表 */
	public List<ResultCard> getResultCardList(int studentId) {
		String sql = "select distinct rd from ResultCard rd  where rd.openTime<=getdate() and rd.studentId="
				+ studentId  ;
		Query q = factory.getCurrentSession().createQuery(sql);
		return q.list();
	}
	
	/** 获取答题卡列表 */
	public List<ResultCard> getResultCardList(String period, String grade,
			String subject, String cate,int studentId) {
		String sql = "from LessonBean a left join a.hierarchy b left join  a.workList c inner join  c.resultCardList d where charindex(',"+period+",',','+a.period+',')>0 and b.grade='"+grade+"' and b.cate="+cate+" and b.subject='"+subject+"' and d.studentId="+studentId ;
		Query q = factory.getCurrentSession().createQuery(sql);
		return q.list();
	}
	
	/** 获取答题卡列表 */
	public List<Map<String,Object>> getResultCards(int lessonId, int studentId) {
		String sql = "select wr.* from WA_ResultCard wr LEFT JOIN WA_Work ww on wr.work = ww.id where wr.opentime<=getdate() and  wr.studentId="+studentId+" and ww.lesson="+lessonId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	

	/** 获取学生答案 */
	public Map<String, Object> getAnswer(int lessonId, int questionId,
			int studentId) {
		String sql = "select rdi.answer from WA_ResultCardInfo rdi left join WA_ResultCard rd on rdi.resultCard=rd.id left join WA_WorkInfo wi on rdi.workInfo=wi.id left join WA_Question q on wi.question=q.id left join WA_Work w on wi.work=w.id where w.lesson="
				+ lessonId
				+ " and q.id="
				+ questionId
				+ " and rd.studentId="
				+ studentId;
		Query q = factory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (Map<String, Object>) q.uniqueResult();
	}
	
	/** 保存答题卡 */
	public void saveResultCard(ResultCard resultCard){
		factory.getCurrentSession().save(resultCard);
	}
	
	/** 获取答题卡 */
	public ResultCard getResultCard(int workId,int studentId){
		String sql = "from ResultCard where work="+workId+" and studentId="+studentId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return  (ResultCard) q.uniqueResult();
	}
	
	public void updateResultCard(ResultCard resultCard){
		factory.getCurrentSession().update(resultCard);
	}
	
	
	/** 删除答题卡详细 */
	public void delResultCardInfo(int resultCardId){
		String sql = "delete from WA_ResultCardInfo where resultCard="+resultCardId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	/** 删除组卷的答题详细  */
	public void delResultCardInfoByWork(int workId){
		String sql = "delete wri from WA_ResultCardInfo wri left join WA_ResultCard wr on wr.id=wri.resultCard where wr.work="+workId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	/** 删除答题卡 */
	public void delResultCard(int resultCardId){
		String sql = "delete from WA_ResultCard where id="+resultCardId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	/** 删除组卷的答题卡  */
	public void delResultCardByWork(int workId){
		String sql = "delete from WA_ResultCard where work="+workId;
		factory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	/** 获取答题卡详细信息 */
	public ResultCardInfo getResultCardInfo(int workInfoId, int resultCardId){
		String sql = "from ResultCardInfo rci where rci.workInfo.iD="+workInfoId+" and rci.resultCard.iD="+resultCardId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (ResultCardInfo) q.uniqueResult();
	}
	
	public void saveResultCardInfo(ResultCardInfo resultCardInfo){
		factory.getCurrentSession().save(resultCardInfo);
	}
	
	public List<Map<String, Object>> getResultCardInfos(int resultCardId){
		String sql = "select wri.*,wm.id mistakeId from WA_ResultCardInfo wri LEFT JOIN WA_WorkInfo wwi on wri.workInfo = wwi.id left join WA_Question wq on wq.id=wwi.question left join WA_Mistakes wm on wm.resultCard=wri.resultCard and wm.workInfo=wri.workInfo where wq.pId is null and wri.resultCard="+resultCardId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public ResultCard getResultCardBean(int resultCardId){
		String sql = "from ResultCard where iD="+resultCardId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (ResultCard) q.uniqueResult();
	}
	
	public ResultCardInfo getResultCardInfoBean(int resultCardId,int questionId){
		String sql = "from ResultCardInfo wri where wri.resultCard.iD= "+resultCardId+" and wri.workInfo.question.iD="+questionId+" order by wri.iD";
		Query q = factory.getCurrentSession().createQuery(sql);
		List<ResultCardInfo> list = q.list();
		if (list==null || list.size()==0) {
			return null;
		}
		return (ResultCardInfo) list.get(0);
	}
	
	public void updateResultCardInfo(ResultCardInfo resultCardInfo){
		factory.getCurrentSession().update(resultCardInfo);
	}
	
	public void saveMistake(Mistake mistake){
		factory.getCurrentSession().save(mistake);
	}
	
	public Mistake getMistake(int workInfoId,int studentId){
		String sql = "from Mistake m where m.workInfo.iD = "+workInfoId+" and m.studentId="+studentId;
		Query q = factory.getCurrentSession().createQuery(sql);
		return (Mistake) q.uniqueResult();
	}
	
	public List<Map<String,Object>> getResultCardListByLandC(int categoryId, int lessonId){
		String sql = "select *,(select name from Edu_Student es where es.id=wr.studentId)studentName,(select code from Edu_Student es where es.id=wr.studentId)studentCode from WA_ResultCard wr where wr.work = (select id from WA_Work ww where ww.category="+categoryId+" and ww.lesson="+lessonId+")";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public List<Map<String,Object>> getResultCardListByLandS(int studentId, int lessonId){
		String sql = "select wr.*,(select categoryName from WA_Category wc where wc.id=ww.category )categoryName,(select name from Edu_Student es where es.id=wr.studentId)studentName from WA_ResultCard wr LEFT JOIN WA_Work ww on ww.id=wr.work LEFT JOIN WA_Lesson wl on ww.lesson=wl.id where wr.studentId="+studentId+" and ww.lesson="+lessonId+" order by ww.category";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public List<Map<String,Object>> getResultCardListByWork(int workId){
		String sql = "select * from WA_ResultCard wr where wr.work ="+workId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public boolean existResultCardListByWork(int workId){
		String sql = "select count(1) count from WA_ResultCard wr where wr.work ="+workId;
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> uniqueResult = (Map<String, Object>) q.uniqueResult();
		return (Integer)uniqueResult.get("count")>0?true:false;
	}
	
	public List<Map<String,Object>> getResultCardInfoList(int resultCardId){
		String sql = "select wri.id,wq.id qId,wwi.sortNo,wq.brief,wq.questionType,wri.state,wri.useTime,wri.answer from WA_ResultCardInfo wri LEFT JOIN WA_WorkInfo wwi on wri.workInfo = wwi.id left join WA_Question wq on wq.id=wwi.question where wq.pid is null and wri.resultCard="+resultCardId+" order by wwi.sortNo";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public List<Map<String,Object>> getChildResultCardInfoList(int parentId,int resultCardId){
		String sql = "select wri.id,wq.id qId,wwi.sortNo,wwi.appSortNo,wq.brief,wq.questionType,wri.state,wri.useTime,wri.answer from WA_ResultCardInfo wri LEFT JOIN WA_WorkInfo wwi on wri.workInfo = wwi.id left join WA_Question wq on wq.id=wwi.question where wq.pid="+parentId+" and wri.resultCard= "+resultCardId+" order by wwi.appSortNo";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	//获取学生上上周的答题卡
	public List<Map<String,Object>> getResultCardListWeek(Map<String,Object> condition){
		String grade = (String) condition.get("grade");
		String cate = (String) condition.get("cate");
		String subject = (String) condition.get("subject");
		int studentId = (Integer) condition.get("studentId");
		int week = (Integer) condition.get("week");
		String weekStartTime = DateUtil.getWeekStartTime(week);
		String weekEndTime = DateUtil.getWeekEndTime(week);
		String sql = "select sum(wr.score)score,wc.id category,wc.categoryName,Convert(decimal(5,4),avg(wr.correct)) correct from WA_ResultCard wr  LEFT JOIN WA_Work ww on ww.id=wr.work LEFT JOIN WA_Category wc on ww.category=wc.id  LEFT JOIN WA_Lesson wl on ww.lesson=wl.id LEFT JOIN WA_Hierarchy wh on wh.id=wl.hierarchy where wr.studentId="+studentId+" and wh.cate='"+cate+"' and wh.grade='"+grade+"' and wh.subject='"+subject+"' and wr.openTime>='"+weekStartTime+"' and wr.openTime<'"+weekEndTime+"' group by ww.category,wc.categoryName,wc.id order by wc.categoryName";		
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public List<Map<String,Object>> getClassScoreAndCorrect(Map<String,Object> condition){
		Object object5 = condition.get("lessonId");
		int lessonId = (Integer) object5;
		String sql = "select ec.id,ec.name"
				+",case when (select count(1) from Edu_StudentClass a LEFT JOIN WA_ResultCard b on a.student=b.studentId LEFT JOIN WA_Work c on c.id=b.work where a.status=1 and c.lesson="+lessonId+" and a.course=ec.id and c.category=1)>0 then 1 else 0 end category1"
				+",case when (select count(1) from Edu_StudentClass a LEFT JOIN WA_ResultCard b on a.student=b.studentId LEFT JOIN WA_Work c on c.id=b.work where a.status=1 and c.lesson="+lessonId+" and a.course=ec.id and c.category=2)>0 then 1 else 0 end category2"
				+",case when (select count(1) from Edu_StudentClass a LEFT JOIN WA_ResultCard b on a.student=b.studentId LEFT JOIN WA_Work c on c.id=b.work where a.status=1 and c.lesson="+lessonId+" and a.course=ec.id and c.category=3)>0 then 1 else 0 end category3"
				+",case when (select count(1) from Edu_StudentClass a LEFT JOIN WA_ResultCard b on a.student=b.studentId LEFT JOIN WA_Work c on c.id=b.work where a.status=1 and c.lesson="+lessonId+" and a.course=ec.id and c.category=4)>0 then 1 else 0 end category4"
				+",case when (select count(1) from Edu_StudentClass a LEFT JOIN WA_ResultCard b on a.student=b.studentId LEFT JOIN WA_Work c on c.id=b.work where a.status=1 and c.lesson="+lessonId+" and a.course=ec.id and c.category=5)>0 then 1 else 0 end category5"
				+ ",max(wr.score) maxScore,min(wr.score) minScore,Convert(decimal(5,2),avg(wr.score)) avgScore,Convert(decimal(5,4),max(wr.correct)) maxCorrect,Convert(decimal(5,4),min(wr.correct)) minCorrect,Convert(decimal(5,4),avg(wr.correct)) avgCorrect " +
				"from WA_ResultCard wr LEFT JOIN WA_Work ww on wr.work=ww.id LEFT JOIN Edu_StudentClass esc on wr.studentId = esc.student LEFT JOIN Edu_Course ec on esc.course=ec.id where esc.status=1";
		Object object = condition.get("grade");
		if (object!=null) {
			String grade = (String) object;
			sql = sql +" and ec.grade='"+grade+"'";
		}
		Object object2 = condition.get("cate");
		if (object2!=null) {
			String cate = (String) object2;
			sql = sql +" and ec.cate='"+cate+"'";
		}
		Object object3 = condition.get("subject");
		if (object3!=null) {
			String subject = (String) object3;
			sql = sql +" and ec.subject='"+subject+"'";
		}
		Object object4 = condition.get("dept");
		if (object4!=null) {
			String dept = (String) object4;
			sql = sql +" and ec.depa='"+dept+"'";
		}
		sql = sql + " and ww.lesson="+lessonId;
		Object object6 = condition.get("periodId");
		if (object6!=null) {
			String periodId = (String) object6;
			sql = sql +" and ec.period in ("+periodId+")";
		}
		Object object7 = condition.get("className");
		if (object7!=null) {
			String className = (String) object7;
			sql = sql +" and ec.name like '%"+className+"%'";
		}
		sql = sql + " GROUP BY ec.id,ec.name order by ec.name";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	public List<Map<String,Object>> getLessonResultCard(int studentId, int lessonId){
		String sql = "select ww.category,Convert(decimal(5,2),wr.score)score,wr.correct,wr.id from Edu_Student es LEFT JOIN WA_ResultCard wr on wr.studentId=es.id LEFT JOIN WA_Work ww on ww.id=wr.work where ww.lesson="+lessonId+" and es.id="+studentId+" order by category";
		Query q = factory.getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
}
