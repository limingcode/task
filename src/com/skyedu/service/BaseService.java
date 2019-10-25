package com.skyedu.service;

import java.util.List;
import java.util.Map;

public interface BaseService {
	
	/** 校区 */
	List<Map<String, Object>> depaList();
	
	/** 学期 */
	List<Map<String, Object>> periodList();

	/** 年级 */
	List<Map<String, Object>> gradeList();
	
	/** 科目 */
	List<Map<String, Object>> subjectList();
	
	/** 层次 */
	List<Map<String, Object>> cateList();
	
	/** 课程 */
	List<Map<String, Object>> courseList(Map<String, Object> map);
	
	/** 在读课次 */
	List<Map<String, Object>> myHierarchys(int student);
	
	/** 班级 */
	Map<String, Object> getCourse(int courseId);
	
	Map<String, Object> getTeacher(String teacherName, String tel);
	
	Map<String, Object> teacherInfo(Integer oaId);
	
}
