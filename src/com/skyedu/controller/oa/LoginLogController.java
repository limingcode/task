package com.skyedu.controller.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.service.BaseService;
import com.skyedu.service.LoginLogService;

/**   
* 类说明
* @author  lisujing
* @version V1.0   
* @Date    2019年7月10日 上午11:47:52  
*/
@Controller
@RequestMapping("/loginLog")
public class LoginLogController {
	@Resource
	private LoginLogService loginLogService;
	@Resource
	private BaseService baseService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("subjects", baseService.subjectList());
		model.addAttribute("grades", baseService.gradeList());
		return "/oa/loginLog";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(String teacherName,Integer subjectId,Integer gradeId,String loginDate,Integer pageNo) {
		List<Map<String,Object>> lists=new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		if (pageNo == null) {
			pageNo = 1;
		}
		condition.put("teacherName", teacherName);
		condition.put("subjectId", subjectId);
		condition.put("loginDate", loginDate);
		condition.put("pageNo", pageNo);
		condition.put("gradeId", gradeId);
		map.put("data", loginLogService.getLoginLogList(condition));
		map.put("condition", condition);
		lists.add(map);
		return map;
	}
}
