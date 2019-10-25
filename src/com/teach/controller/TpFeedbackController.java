package com.teach.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.service.BaseService;
import com.teach.Page;
import com.teach.service.TpFeedbackService;

import net.sf.json.JSONObject;

/**
 * 课后反馈controller
 * @author huangdebin
 * @version 1.0
 */
@Controller
@RequestMapping("/feedback")
public class TpFeedbackController {

	@Resource
	private TpFeedbackService feedback;
	@Resource
	private BaseService baseService;
	
	/**
	 * 反馈信息
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toFeedBack", method=RequestMethod.GET)
	public String toFeedBack(String lessonId, Model model) {
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> cateList = baseService.cateList();
		List<Map<String, Object>> periodList = baseService.periodList();
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("cateList", cateList);
		model.addAttribute("periodList", periodList);
		if(StringUtils.isNotEmpty(lessonId)) {
			Map<String, Object> lessonInfo = feedback.getLessonInfo(Integer.parseInt(lessonId));
			model.addAttribute("data", lessonInfo);
		}
//		return "teach/feedBack/feedBack";
		return "teach_v2/feedbackList";
	}
	
	/**
	 * 得到反馈列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getFeedbackList", method=RequestMethod.POST)
	public String getFeedbackList(Page page, String lessonId, String beginTime, String lessons, String semester, 
			String grade, String arrangement, String classTime, String name, String state) {
		page = feedback.getPage(page, lessonId, beginTime, lessons, semester, grade, arrangement, classTime, name, state);
		page.setStatus(true);
		page.setMessage("success");
		JSONObject json = JSONObject.fromObject(page);
		return json.toString();
	}
}
