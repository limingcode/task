package com.skyedu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyedu.dao.impl.RoleDAO;
import com.skyedu.model.UserRole;
import com.skyedu.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDAO roleDao;
	
	@Override
	public List<String> getRoleList() {
		return roleDao.getRoleList();
	}

	@Override
	public List<String> getUserRoleList(int oaId) {
		return roleDao.getUserRoleList(oaId);
	}

	@Override
	public List<Map<String, Object>> getEmployeeList(Map<String,Object> condition) {
		return roleDao.getEmployeeList(condition);
	}

	@Override
	public List<Map<String, Object>> getRoleMapList() {
		return roleDao.getRoleMapList();
	}

	@Override
	public UserRole getUserRole(int oaId,int roleId) {
		return roleDao.getUserRole(oaId, roleId);
	}

	@Override
	public boolean saveOrUpdateUserRole(UserRole userRole) {
		return roleDao.saveOrUpdateUserRole(userRole);
	}

}
