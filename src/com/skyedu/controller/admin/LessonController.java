package com.skyedu.controller.admin;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.model.Hierarchy;
import com.skyedu.service.BaseService;
import com.skyedu.service.HierarchyService;
import com.skyedu.service.LessonService;
import com.skyedu.service.UserService;
import com.skyedu.service.WorkService;
import com.teach.service.TpCoursewareService;

@Controller
@RequestMapping("/lesson")
public class LessonController {

	@Autowired
	private BaseService baseService;
	@Autowired
	private LessonService lessonService;
	@Autowired
	private HierarchyService hierarchyService;
	@Autowired
	private WorkService workService;
	@Autowired
	private TpCoursewareService coursewareService;
	@Autowired
	private UserService userService;

	/**
	 * 课次列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 *
	 */
	@RequestMapping("/lessonList")
	public String getLessonList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String period = request.getParameter("period");
		String lessonIdd = request.getParameter("lessonId");
		String grade = "";
		String cate = "";
		String subject = "";
		if (StringUtils.isEmpty(lessonIdd)) {
			//没有课次id
			grade = request.getParameter("grade");
			cate = request.getParameter("cate");
			subject = request.getParameter("subject");
			condition.put("period", period);
			condition.put("grade", grade);
			condition.put("cate", cate);
			condition.put("subject", subject);
		} else {
			//有课次id
			Map<String, Object> lessonInfo = lessonService.getLessonInfo(Integer.valueOf(lessonIdd));
			grade = (String) lessonInfo.get("grade");
			cate = (String) lessonInfo.get("cate");
			subject = (String) lessonInfo.get("subject");
			period = (String)lessonInfo.get("period");
			condition.put("period", period);
			condition.put("grade", grade.trim());
			condition.put("cate", cate.trim());
			condition.put("subject", subject.trim());
		}
		List<Map<String, Object>> cateList = baseService.cateList();
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> periodList = baseService.periodList();
		List<Map<String, Object>> lessonList = null;
		// 学期，年级，科目，层次都满足条件才查询对应的课次
		if (!StringUtils.isEmpty(period) && !StringUtils.isEmpty(grade)
				&& !StringUtils.isEmpty(subject) && !StringUtils.isEmpty(cate)) {
			lessonList = lessonService.getLessonList(period, grade, subject,
					cate);
		}
		modelMap.addAttribute("periodList", periodList);
		modelMap.addAttribute("cateList", cateList);
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
		modelMap.addAttribute("lessonList", lessonList);
		modelMap.addAttribute("condition", condition);
		return "admin/lesson/lessonList";
	}

	/**
	 * 提交新建课次
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/commitLesson")
	@Transactional
	public String commitLesson(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String period = request.getParameter("period");
		String grade = request.getParameter("grade");
		String cate = request.getParameter("cate");
		String subject = request.getParameter("subject");
		List<Map<String, Object>> lessonList = null;
		// 学期，年级，科目，层次都满足条件才查询对应的课次
		if (!StringUtils.isEmpty(period) && !StringUtils.isEmpty(grade)
				&& !StringUtils.isEmpty(subject) && !StringUtils.isEmpty(cate)) {
			lessonList = lessonService.getLessonList(period, grade, subject,
					cate);
			Hierarchy hierarchy = hierarchyService.getHierarchy(grade, subject,
					cate);
			Map<String, Object> lesson = new HashMap<String, Object>();
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
			lesson.put("sortNo", lessonList.size() + 1);
			lesson.put("hierarchy", hierarchy.getiD());
			lesson.put("period", period);
			lesson.put("brief", "");
			lesson.put("createDate", sd.format(new Date()));
			lesson.put("modifyDate", sd.format(new Date()));
			//默认开放时间为创建时间的一周以后
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			calendar.add(Calendar.DATE, 7);
			String openTime = sdf.format(calendar.getTime());

			lesson.put("openTime", openTime);
			//保存新建课次
			lessonService.commitLesson(lesson);
		}
		// return "admin/lesson/lessonList";
		return "redirect:/lesson/lessonList.jhtml?period=" + period + "&grade="
				+ grade + "&subject=" + subject + "&cate=" + cate;
	}

	/**
	 * 删除课次
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delLesson")
	@ResponseBody
	@Transactional
	public Map<String, Object> delLesson(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String period = request.getParameter("period");
		if (StringUtils.isEmpty(period)) {
			map.put("code", 101);
			map.put("message", "参数错误");
			return map;
		}
		String grade = request.getParameter("grade");
		String cate = request.getParameter("cate");
		String subject = request.getParameter("subject");
		// 学期，年级，科目，层次都满足条件才查询对应的课次
		if (!StringUtils.isEmpty(period) && !StringUtils.isEmpty(grade)
				&& !StringUtils.isEmpty(subject) && !StringUtils.isEmpty(cate)) {
			Hierarchy hierarchy = hierarchyService.getHierarchy(grade, subject,
					cate);
			try {
				Map<String, Object> maxLesson = lessonService.getMaxLesson(hierarchy.getiD(), period);
				int lessonId = (Integer) maxLesson.get("id");
				List<Map<String, Object>> works = workService.getWorks(lessonId, 0);
				if (works == null || works.size()==0) {
					//删除最大的课次，倒序删除
					coursewareService.deleteCourseware(lessonId);
					lessonService.deleteMaxLesson(lessonId);
					//删除静态文件夹
					HttpSession session = request.getSession();
					String path = session.getServletContext()
							.getRealPath("/");
					String lessonPath = path + File.separatorChar + "upload"
							+ File.separatorChar + "lesson"
							+ File.separatorChar + lessonId % 15
							+ File.separatorChar + lessonId % 16
							+ File.separatorChar + lessonId;
					File file = new File(path + lessonPath);
					if (file.exists()) {
						FileUtils.deleteQuietly(file);
					}
					Map<String, Object> hierarchyInfo = hierarchyService.getHierarchyInfo(hierarchy.getiD());
					userService.setDelRecord((Integer)session.getAttribute("oaId"), (String)session.getAttribute("teacherName"),
							"删除"+maxLesson.get("year")+maxLesson.get("periodName")+hierarchyInfo.get("gradeName")+hierarchyInfo.get("subjectName")+hierarchyInfo.get("cateName")+"第"+maxLesson.get("sortNo")+"课次", 0);
					map.put("code", 100);
					map.put("message", "删除成功");
				}else{
					map.put("code", 101);
					map.put("message", "删除失败，该课次下有相关数据，不允许删除");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				map.put("code", 101);
				map.put("message", "删除失败，该课次下有相关数据，不允许删除");
			}
		}
		return map;
	}

	/**
	 * 修改课次
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping("/updateLesson")
	public void updateLesson(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		String lessonId = request.getParameter("lessonId");
		String openTime = request.getParameter("openTime");
		if (!StringUtils.isEmpty(openTime)) {
			String time = openTime.replaceAll("[\\u4e00-\\u9fa5]", "");
			lessonService.updateOpenTime(Integer.valueOf(lessonId), time);
		} else {
			String brief = request.getParameter("brief");
			lessonService.updateBrief(Integer.valueOf(lessonId), brief);
		}
	}
	
	/**
	 * 修改课次大纲
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	/*@RequestMapping("/updateLessonBrief")*/
	public void updateLessonBrief(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		String lessonId = request.getParameter("lessonId");
		String brief = request.getParameter("brief");
		lessonService.updateBrief(Integer.valueOf(lessonId), brief);
	}
}
