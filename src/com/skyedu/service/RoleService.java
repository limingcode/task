package com.skyedu.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.skyedu.model.UserRole;

public interface RoleService {

	List<String> getRoleList();
	
	List<Map<String,Object>> getRoleMapList();
	
	List<String> getUserRoleList(int oaId);
	
	List<Map<String,Object>> getEmployeeList(Map<String,Object> condition);
	
	UserRole getUserRole(int oaId,int roleId);
	
	boolean saveOrUpdateUserRole(UserRole userRole);
}
