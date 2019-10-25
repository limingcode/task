package com.skyedu.controller.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.model.ResultCard;
import com.skyedu.service.LessonService;
import com.skyedu.service.ResultCardService;

@Controller
@RequestMapping("/appLesson")
public class AppLessonController {

	@Autowired
	private LessonService lessonService;
	@Autowired
	private ResultCardService resultCardService;

	@RequestMapping(value = "/resultCardList")
	@ResponseBody
	public void getLessonInfo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		Map<String, Object> result = new HashMap<String, Object>();
		String lessonId = request.getParameter("lessonId");
		String studentId = request.getParameter("userStudentId");
		if (StringUtils.isEmpty(lessonId) || StringUtils.isEmpty(studentId)) {
			result.put("code", 101);
			result.put("message", "非法参数");
		} else {
			// List<ResultCard> resultCards =
			// lessonService.getLessonResultCardList(Integer.valueOf(lessonId),
			// Integer.valueOf(studentId));
			List<Map<String, Object>> resultCards = lessonService.getLessonResultCardList(Integer.valueOf(lessonId),
					Integer.valueOf(studentId));
			result.put("code", 100);
			result.put("data", resultCards);
		}
		response.getOutputStream().write(JSONObject.toJSONString(result).getBytes("UTF-8"));
	}

	@RequestMapping(value="/getModifyDate")
	@ResponseBody
	public Map<String,Object> getModifyDate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
		Map<String,Object> result = new HashMap<String, Object>();
		String lessonId = request.getParameter("lessonId");
		String studentId = request.getParameter("userStudentId");
		if (StringUtils.isEmpty(lessonId) || StringUtils.isEmpty(studentId)) {
			result.put("code", 101);
			result.put("message", "非法参数");
		} else{
			Map<String, Object> lessonInfo = lessonService.getLessonInfo(Integer.valueOf(lessonId));
			lessonInfo.put("modifyDate", new Date(0));
			List<Map<String,Object>> resultCardList = resultCardService.getResultCardListByLandS( Integer.valueOf(studentId),Integer.valueOf(lessonId));
			int unDealed = 0;
			Double starRCorrect = 0.00;
			for (Iterator<Map<String,Object>> iterator3 = resultCardList.iterator(); iterator3
					.hasNext();) {
				Date lessonModifyDate = (Date) lessonInfo.get("modifyDate");
				Map<String,Object> resultCard = (Map<String,Object>) iterator3.next();
				Integer hasDealed = (Integer) resultCard.get("hasDealed");
				Date resultCardModifyDate = (Date) resultCard.get("createDate");
				if (resultCardModifyDate.getTime()>lessonModifyDate.getTime()) {
					lessonInfo.put("modifyDate", resultCardModifyDate);
				}
				if (0==hasDealed) {
					unDealed ++;
				}
				String correct = resultCard.get("correct")==null?"0":((Double)resultCard.get("correct")).toString();
				if (!StringUtils.isEmpty(correct)) {
					Double RCorrect = Double.parseDouble(correct);
					starRCorrect = starRCorrect + RCorrect;
				}
			}
			int starCount = 0;
			double starCorrect = starRCorrect/resultCardList.size();
			if (starCorrect<=0.50) {
				
			}else if(starCorrect<=0.70){
				starCount = 1;
			}else if(starCorrect<=0.90){
				starCount = 2;
			}else if(starCorrect>0.90){
				starCount = 3;
			}
			Date modifyDate = (Date) lessonInfo.get("modifyDate");
			long time = modifyDate.getTime()+resultCardList.size();
			lessonInfo.put("modifyDate", new Date(time));
			lessonInfo.put("unDealed", unDealed);
			lessonInfo.put("count", resultCardList.size());
			lessonInfo.put("starCount", starCount);
			result.put("code", 100);
			result.put("data", lessonInfo);
		}
		return result;
	}

	@RequestMapping(value = "/getModifyDateList")
	@ResponseBody
	public Map<String, Object> getModifyDateList(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> modifyDateList = new ArrayList<Map<String, Object>>();
		String courseId = request.getParameter("courseId");
		String studentId = request.getParameter("userStudentId");
		if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(studentId)) {
			result.put("code", 101);
			result.put("message", "非法参数");
		} else {
			// 通过课程id获取所有课次
			List<Map<String, Object>> lessonList = lessonService.getLessonListByCourse(Integer.valueOf(courseId));
			for (Iterator<Map<String, Object>> iterator = lessonList.iterator(); iterator.hasNext();) {
				Map<String, Object> lesson = (Map<String, Object>) iterator.next();
				int lessonId = (Integer) lesson.get("id");
				Map<String, Object> lessonInfo = lessonService.getLessonInfo(lessonId);
				lessonInfo.put("modifyDate", new Date(0));
				// 获取该学生该课次的所有作业
				List<ResultCard> resultCardList = resultCardService.getResultCardList(lessonId,
						Integer.valueOf(studentId));
				int unDealed = 0;
				Double starRCorrect = 0.00;
				for (Iterator<ResultCard> iterator3 = resultCardList.iterator(); iterator3.hasNext();) {
					Date lessonModifyDate = (Date) lessonInfo.get("modifyDate");
					ResultCard resultCard = (ResultCard) iterator3.next();
					Integer hasDealed = (Integer) resultCard.getHasDealed();
					Date resultCardModifyDate = resultCard.getCreateDate();
					if (resultCardModifyDate.getTime() > lessonModifyDate.getTime()) {
						lessonInfo.put("modifyDate", resultCardModifyDate);
					}
					if (0==hasDealed) {
						unDealed ++;
					}
					String correct = resultCard.getCorrect()==null?"0":resultCard.getCorrect().toString();
					if (!StringUtils.isEmpty(correct)) {
						Double RCorrect = Double.parseDouble(correct);
						starRCorrect = starRCorrect + RCorrect;
					}
				}
				// lesson的modifyData
				Date modifyDate = (Date) lessonInfo.get("modifyDate");
				long time = modifyDate.getTime() + resultCardList.size();
				Map<String, Object> lessonData = new HashMap<String, Object>();
				int starCount = 0;
				double starCorrect = starRCorrect/resultCardList.size();
				if (starCorrect<=0.50) {
					
				}else if(starCorrect<=0.70){
					starCount = 1;
				}else if(starCorrect<=0.90){
					starCount = 2;
				}else if(starCorrect>0.90){
					starCount = 3;
				}
				lessonData.put("lessonId", lessonId);
				lessonData.put("modifyDate", new Date(time));
				lessonData.put("openTime", lessonInfo.get("openTime"));
				lessonData.put("unDealed", unDealed);
				lessonData.put("count", resultCardList.size());
				lessonData.put("starCount", starCount);
				modifyDateList.add(lessonData);
			}
			result.put("code", 100);
			result.put("data", modifyDateList);
		}
		return result;
	}
}
