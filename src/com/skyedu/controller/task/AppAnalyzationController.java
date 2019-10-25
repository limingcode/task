package com.skyedu.controller.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.service.AnalyzationService;

@Controller
@RequestMapping("/appAnalyzation")
public class AppAnalyzationController {

	@Autowired
	private AnalyzationService analyzationService;
	
	@RequestMapping("/getAnalyzationList")
	@ResponseBody
	public Map<String,Object> getRankList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> data = new HashMap<String, Object>();
		String cate = request.getParameter("cate");
		String subject = request.getParameter("subject");
		String grade = request.getParameter("grade");
		String studentId = request.getParameter("userStudentId");
		if (StringUtils.isEmpty(cate)||StringUtils.isEmpty(subject)||StringUtils.isEmpty(grade)||StringUtils.isEmpty(studentId)) {
			result.put("code", 101);
			result.put("message", "参数异常");
		}else {
			Map<String, Object> nearlyCorrect = analyzationService.getNearlyCorrect(cate, subject, grade, Integer.valueOf(studentId));
			List<Map<String, Object>> rankList = analyzationService.getRankList(cate, subject, grade, Integer.valueOf(studentId));
			List<Map<String, Object>> correctList = analyzationService.getCorrectList(cate, subject, grade, Integer.valueOf(studentId));
			data.put("nearlyCorrect", nearlyCorrect);
			data.put("rankList", rankList);
			data.put("correctList", correctList);
			result.put("code", 100);
			result.put("data", data);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getEvaluationVersion")
	public Map<String,Object> getEvaluationVersion(){
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("version", 0);
		result.put("code", 100);
		result.put("data", data);
		return result;
	}
}
