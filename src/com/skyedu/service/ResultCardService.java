package com.skyedu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.skyedu.model.Mistake;
import com.skyedu.model.ResultCard;
import com.skyedu.model.ResultCardInfo;
import com.skyedu.model.Work;

public interface ResultCardService {

	List<ResultCard> getResultCardList(int lessonId, int studentId);
	/**
	 * 发布作业，生成答题卡
	 * @param workId
	 * @param studentId
	 * @param openTime
	 */
	boolean publishWork(Work work, int studentId, Date openTime);
	
	/**
	 * 移除作业
	 * @param workId
	 * @param studentId
	 */
	void removeWork(int workId, int studentId);
	
	/**
	 * 获取答题卡
	 * @param workId
	 * @param studentId
	 * @return
	 */
	ResultCard getResultCard(int workId,int studentId);
	
	void updateResultCard(ResultCard resultCard);
	
	ResultCard getResultCardBean(int resultCardId);
	
	ResultCardInfo getResultCardInfoBean(int resultCardId,int questionId);
	
	void updateResultCardInfo(ResultCardInfo resultCardInfo);
	
	void saveMistake(Mistake mistake);
	
	Mistake getMistake(int workInfoId,int studentId);
	
	List<Map<String,Object>> getResultCardListByLandC(int categoryId, int lessonId);
	
	List<Map<String,Object>> getResultCardListByLandS(int studentId, int lessonId);
	
	List<Map<String,Object>> getResultCardListByWork(int workId);
	
	boolean existResultCardListByWork(int workId);
	
	List<Map<String,Object>> getResultCardInfoList(int resultCardId);
	
	/**
	 * 获取学生上周的答题卡
	 * @param condition
	 * @return
	 */
	List<Map<String,Object>> getResultCardListWeek(Map<String,Object> condition);
	
	/**
	 * 获取班级的分数和正确率
	 * @param condition
	 * @return
	 */
	List<Map<String,Object>> getClassScoreAndCorrect(Map<String,Object> condition);
	
	/**
	 * 获取课次下某个学生的所有答题卡成绩
	 * @param studentId
	 * @param lessonId
	 * @return
	 */
	List<Map<String,Object>> getLessonResultCard(int studentId, int lessonId);
	
	/** 
	 * 删除组卷的答题详细  
	 */
	void delResultCardInfoByWork(int workId);
	
	/** 
	 * 删除组卷的答题卡  
	 */
	void delResultCardByWork(int workId);
	
	List<ResultCard> getResultCardList(String period, String grade,
			String subject, String cate,int studentId);
}
