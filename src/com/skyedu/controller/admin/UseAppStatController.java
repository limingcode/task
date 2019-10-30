package com.skyedu.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.Page;
import com.skyedu.service.BaseService;
import com.skyedu.service.UseAppStatService;

@Controller
@RequestMapping("/useAppStat")
public class UseAppStatController {
	
	@Autowired
	private UseAppStatService useAppStatService;
	@Autowired
	private BaseService baseService;
	
	@RequestMapping("/goAppAndWorkStat")
	public String goStudentLoginAppStat(Model model) {
		model.addAttribute("gradeList", baseService.gradeList());
		return "statis/useAppSata/appAndWorkStatList";
	}
	
	@RequestMapping("/exportExcel")
	public String exportExcel(int type, Model model) {
		model.addAttribute("list", useAppStatService.exportExcel(type));
		return "statis/useAppSata/export";
	}
	
	@ResponseBody
	@RequestMapping("/getAppAndWorkStat")
	public Page getAppAndWorkStat(Page page, int type, String className, String studentNum, 
			String studentName, String teacherName,String grade, String loginDateStart, String loginDateEnd) {
		page = useAppStatService.appAndWorkStat(page, type, className, studentNum, studentName, teacherName, grade, loginDateStart, loginDateEnd);
		return page;
	}

	@ResponseBody
	@RequestMapping("/getLoginNum")
	public Map<String, Object> getLoginNum(String D1, String D2){
		if (StringUtils.isEmpty(D1) || StringUtils.isEmpty(D2)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "参数错误！");
			return map;
		}
		return useAppStatService.getLoginNum(D1.compareTo(D2) > 0 ? D2 : D1, D1.compareTo(D2) > 0 ? D1 : D2);
	}
	
}
