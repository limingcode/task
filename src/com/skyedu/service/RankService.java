package com.skyedu.service;

import java.util.List;
import java.util.Map;

public interface RankService {

	//获取同一个综合层次下的人数
	int getRankCount(Map<String,Object> condition);
	
	//设置称号
	void setDesigntion(Map<String,Object> condition);
	
	//获取有称号的学生的列表
	List<Map<String,Object>> getStudentList(Map<String,Object> condition);
	
	//获取学生的排行信息
	Map<String,Object> getStudent (Map<String,Object> condition);
	
	//获取排行榜前100名数据列表
	List<Map<String,Object>> getRankStudentList(Map<String,Object> condition);
	
	//获取学生上周排名称号
	List<Map<String,Object>> getDesignationList(int studentId);
}
