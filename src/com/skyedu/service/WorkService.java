package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;

public interface WorkService {

	/**
	 * 获取组卷信息
	 * @param workId
	 * @return
	 */
	Map<String, Object> getWork(int workId);

	/**
	 * 获取课次下的组卷
	 * @param lessonId
	 * @param categoryId 0即为所有
	 * @return
	 */
	List<Map<String, Object>> getWorks(int lessonId, int categoryId);

	/**
	 * 获取组卷详细
	 * @param workId
	 * @param flag 是否为所有详细，false即不获取子题
	 * @return
	 */
	List<WorkInfo> getWorkInfoList(int workId, boolean flag);

	/**
	 * 保存组卷
	 * @param work
	 */
	void saveWork(Work work);

	/**
	 * 删除组卷
	 * @param workId
	 */
	void delWork(int workId);

	/**
	 * 删除组卷详细
	 * @param workInfoId
	 */
	void delWorkInfo(int workInfoId);

	/**
	 * 保存组卷详细
	 * @param workInfo
	 */
	void saveWorkInfo(WorkInfo workInfo);

	/**
	 * 获取组卷详细
	 * @param workId 
	 * @param questionId 对应的题目Id
	 * @return
	 */
	WorkInfo getWorkInfo(int workId, int questionId);
	
	/**
	 * 更新组卷
	 * @param work
	 */
	void updateWork(Work work);
	
	/**
	 * 获取组卷bean
	 * @param workId
	 * @return
	 */
	Work getWorkBean(int workId);
	
	/**
	 * 引用了题目的组卷列表
	 * @param questionId
	 * @return
	 */
	List<Work> getWorksByQ(int questionId);
	
	/**
	 * 更新组卷信息，有答题卡引用则不更新返回false
	 * @param work
	 * @return
	 */
	boolean existResultCardByWork(Work work);

	/**
	 * 作业正确率统计
	 * @param type
	 * @param grade
	 * @param gradeId
	 * @param  
	 * @return
	 */
	List<Map<String, Object>> workCorrectStatistics(int type, String subject, String period, String grade, String gradeId);

	/**
	 * 学生作业正确率统计
	 * @param type
	 * @param grade
	 * @param gradeId
	 * @param  
	 * @return
	 */
	List<Map<String, Object>> studentWorkCorrectStatis(int courseId, String studentId);
}
