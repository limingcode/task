package com.skyedu.service;

import java.util.List;
import java.util.Map;

public interface CourseService {

	/**
	 * 获取学生报班的层次列表
	 * @param studentId
	 * @return
	 */
	List<Map<String,Object>> getHierarchyList(int studentId);
	
	/**
	 * 获取班级里面的所有学生
	 * @param courseId
	 * @return
	 */
	List<Map<String,Object>> getStudentList(int courseId,boolean flag);
	
	/**
	 * 模糊查询名字所报的班级信息
	 * @param studentName
	 * @return
	 */
	List<Map<String,Object>> searchStudent(String studentName);
	
	/** 获取学生班级详细信息列表 */
	List<Map<String, Object>> getCourseInfoList(int studentId);
	
	/** 获取所有没有更新上课时间的课程，定时任务查找并进行更新 */
	List<Map<String, Object>> getCourseList();
	
	/** 更新课程的上课时间 */
	void setCourseTime(String courseTime,int courseId);

	List<Map<String, Object>> getHierarchyListNew(int studentId);
}
