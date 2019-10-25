package com.skyedu.service;

import java.util.List;
import java.util.Map;

import com.skyedu.model.StudentLoginLog;

public interface UserService {

	/**
	 * 学生登录信息
	 * @param username
	 * @param password
	 * @return
	 */
	Map<String, Object> getStudent(String username, String password);

    /**
     *
     * @param username
     * @param password
     * @return
     */
	Map<String, Object> getStudentInfo(String username, String password);

	/** 我的头像 */
	void changImg(int studentId, String imgUrl, int cityId);
	
	public void changTip(int studentId, int tip);
	
	/** 学生信息 */
	Map<String,Object> getStudent(int studentId,int cityId);

	Map<String,Object> getStudent(int studentId);

	void saveStudentLoginLog(StudentLoginLog studentLoginLog);
	
	StudentLoginLog getStudentLoginLog(int studentId);
	
	/**
	 * 后台登陆信息
	 * @param username
	 * @return
	 */
	List<Map<String,Object>> getOaId(String username);
	
	void setDelRecord(int oaId,String name,String content,int type);

	void saveAppBugLog(Map<String, String[]> parameterMap);
}
