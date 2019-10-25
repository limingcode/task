package com.teach.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.service.BaseService;
import com.teach.Page;
import com.teach.service.StatisticsService;


/**
 * 教学统计控制器
 * @author skyedu_beyond
 *
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	@Resource
	private BaseService baseService;
	@Resource
	private StatisticsService statisticsService;

	@RequestMapping(value="/lessonStatistics", method=RequestMethod.GET)
	public String lessonStatistics(Model model) {
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> cateList = baseService.cateList();
		List<Map<String, Object>> periodList = baseService.periodList();
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("cateList", cateList);
		model.addAttribute("periodList", periodList);
		return "teach/statistics/lessonUpload"; 
	}
	
	@ResponseBody
	@RequestMapping(value="/getLessonUploadStatus", method=RequestMethod.POST)
	public String getLessonUploadStatus(Page page, String subject, String period, String grade, String cate) {
		page = statisticsService.getLessonUploadStatus(page, subject, period, grade, cate);
		return JSONObject.toJSONString(page);
	}
	
	@RequestMapping(value="/lessonStatisticsV2", method=RequestMethod.GET)
	public String lessonStatisticsV2(Model model) {
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> periodList = baseService.periodList();
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("periodList", periodList);
		return "teach/statistics/lessonUpload_V2"; 
	}
	
	@ResponseBody
	@RequestMapping(value="/getLessonUploadStatusV2", method=RequestMethod.POST)
	public String getLessonUploadStatusV2(String subject, String period) {
		List<Map<String, Object>> list = statisticsService.getLessonUploadStatus(subject, period);
		return JSONObject.toJSONString(list);
	}
}
