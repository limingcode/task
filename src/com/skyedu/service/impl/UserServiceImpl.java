package com.skyedu.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.UserDAO;
import com.skyedu.model.AppBugLog;
import com.skyedu.model.StudentLoginLog;
import com.skyedu.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Map<String, Object> getStudent(String username, String password) {
		return userDAO.getStudent(username, password);
	}

    @Override
    public Map<String, Object> getStudentInfo(String username, String password) {
        return userDAO.getStudentInfo(username, password);
    }

    @Override
	public void changImg(int studentId, String imgUrl, int cityId) {
		userDAO.changImg(studentId, imgUrl, cityId);
	}

	@Override
	public Map<String,Object> getStudent(int studentId,int cityId){
		return userDAO.getStudent(studentId,cityId);
	}

    @Override
    public Map<String, Object> getStudent(int studentId) {
        return userDAO.getStudent(studentId);
    }

    @Override
	public List<Map<String, Object>> getOaId(String username) {
		return userDAO.getOaId(username);
	}

	@Override
	public void changTip(int studentId, int tip) {
		userDAO.changTip(studentId, tip);
	}

	@Override
	public void setDelRecord(int oaId, String name, String content, int type) {
		userDAO.setDelRecord(oaId, name, content, type);
	}

	@Override
	public void saveStudentLoginLog(StudentLoginLog studentLoginLog) {
		userDAO.saveStudentLoginLog(studentLoginLog);
	}

	@Override
	public StudentLoginLog getStudentLoginLog(int studentId) {
		return userDAO.getStudentLoginLog(studentId);
	}
	
	@Override
	public void saveAppBugLog(Map<String, String[]> params) {
		Set<Entry<String, String[]>> entrySet = params.entrySet();
		String uuid = UUID.randomUUID().toString();
		for (Iterator<Entry<String, String[]>> iterator = entrySet.iterator(); iterator.hasNext();) {
			Entry<String, String[]> entry = (Entry<String, String[]>) iterator.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
			String string = Arrays.toString(value);
			String va = string.length()>1500?string.substring(0, 1500):string;
			AppBugLog appBugLog = new AppBugLog(uuid, key, va, new Date());
			userDAO.saveAppBugLog(appBugLog);
		}
		
	}
}
