package com.skyedu.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.model.UserRole;
import com.skyedu.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/index")
	public String getUserRoleList(ModelMap map,@RequestParam(required=false) Integer pageNo,@RequestParam(required=false)Integer pageSize,@RequestParam(required=false)String username,@RequestParam(required=false)String deptName){
		if (pageNo==null || pageNo<1) {
			pageNo = 1;
		}
		if (pageSize==null || pageSize<1) {
			pageSize = 15;
		}
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("pageNo", pageNo);
		condition.put("pageSize", pageSize);
		condition.put("username", username);
		condition.put("deptName", deptName);
		List<Map<String, Object>> roleMapList = roleService.getRoleMapList();
		List<Map<String, Object>> employeeList = roleService.getEmployeeList(condition);
		map.put("roleMapList", roleMapList);
		map.put("employeeList", employeeList);
		map.put("condition", condition);
		return "admin/role/index";
	}
	
	@RequestMapping(value="/change",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveUserRole(@RequestParam(required=true) Integer eoaId,@RequestParam(required=true) Integer roleId,@RequestParam(required=true) Integer state){
		Map<String,Object> map = new HashMap<String, Object>();
		UserRole userRole = roleService.getUserRole(eoaId, roleId);
		if (userRole==null) {
			userRole = new UserRole(eoaId, roleId, state);
		}else{
			userRole.setState(state);
		}
		boolean saveOrUpdateUserRole = roleService.saveOrUpdateUserRole(userRole);
		if (saveOrUpdateUserRole) {
			map.put("code", 100);
		}else {
			map.put("code", 101);
			map.put("message", "权限修改失败,请联系管理员");

		}
		return map;
	}
}
