package com.skyedu.controller.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.model.Category;
import com.skyedu.model.Question;
import com.skyedu.model.ResultCard;
import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;
import com.skyedu.service.BaseService;
import com.skyedu.service.CategoryService;
import com.skyedu.service.CourseService;
import com.skyedu.service.LessonService;
import com.skyedu.service.MistakeService;
import com.skyedu.service.QuestionService;
import com.skyedu.service.ResultCardService;
import com.skyedu.service.UserService;
import com.skyedu.service.WorkService;
import com.util.FileUtil;
import com.util.WorkThread;

@Controller
@RequestMapping("/work")
public class WorkController {

	@Autowired
	private WorkService workService;
	@Autowired
	private LessonService lessonService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private ResultCardService resultCardService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private MistakeService mistakeService;
	@Autowired
	private UserService userService;

	@RequestMapping("/commitWork")
	public String commitWork(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String category = request.getParameter("category");
		String lesson = request.getParameter("lesson");
		int oaId = (Integer) request.getSession().getAttribute("oaId");
		if (StringUtils.isEmpty(category) || StringUtils.isEmpty(lesson)) {
			return "";
		}
		List<Map<String, Object>> works = workService.getWorks(
				Integer.valueOf(lesson), Integer.valueOf(category));
		Category categoryBean = categoryService.getCategoryBean(Integer.valueOf(category));
		if (works == null || works.size() == 0) {
			Work work = new Work();
			work.setCategory(categoryBean);
			work.setCreateDate(new Date());
			work.setLesson(lessonService.getLessonBean(Integer.valueOf(lesson)));
			work.setModifyDate(new Date());
			work.setOaId(oaId);
			work.setScore(0.0);
			work.setStandardTime(0);
			work.setWorkName("");
			work.setZipState(0);
			workService.saveWork(work);
			lessonService.updateLesson(Integer.valueOf(lesson));
		}
		return "redirect:/work/workList.jhtml?lesson=" + lesson;
	}

	@RequestMapping("/workList")
	public String getWorkList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String lesson = request.getParameter("lesson");
		if (StringUtils.isEmpty(lesson)) {
			return "";
		}
		// 课次信息
		Map<String, Object> lessonInfo = lessonService.getLessonInfo(Integer
				.valueOf(lesson));
		// 获取课次的作业列表
		List<Map<String, Object>> workList = workService.getWorks(
				Integer.valueOf(lesson), 0);
		// 获取题类列表
		List<Map<String, Object>> categorys = categoryService.getCategoryList();
		List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
		categoryList.addAll(categorys);
		for (Iterator<Map<String, Object>> iterator2 = categorys.iterator(); iterator2
				.hasNext();) {
			Map<String, Object> category = (Map<String, Object>) iterator2
					.next();
			for (Iterator<Map<String, Object>> iterator = workList.iterator(); iterator
					.hasNext();) {
				Map<String, Object> work = (Map<String, Object>) iterator
						.next();
				if (category.get("id").equals(work.get("category"))) {
					categoryList.remove(category);
				}
			}
		}
		modelMap.put("workList", workList);
		modelMap.put("lesson", lessonInfo);
		modelMap.put("categoryList", categoryList);
		return "admin/work/workList";
	}

	@RequestMapping("/workInfoList")
	public String getWorkInfoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String workId = request.getParameter("work");
		if (StringUtils.isEmpty(workId)) {
			return "";
		}
		Map<String, Object> work = workService.getWork(Integer.valueOf(workId));
		// 课次信息
		Map<String, Object> lessonInfo = lessonService
				.getLessonInfo((Integer) work.get("lesson"));
		List<WorkInfo> workInfoList = workService.getWorkInfoList(
				Integer.valueOf(workId), false);
		modelMap.put("workInfoList", workInfoList);
		modelMap.put("lesson", lessonInfo);
		modelMap.put("work", work);
		return "admin/work/workInfoList";
	}

//	@RequestMapping("/delWork")
//	@ResponseBody
//	@Transactional
	public String delWork(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String workId = request.getParameter("workId");
		if (!StringUtils.isEmpty(workId)) {
			try {
				if (!resultCardService.existResultCardListByWork(Integer.valueOf(workId))) {
					Map<String, Object> work = workService.getWork(Integer
							.valueOf(workId));
					String path = request.getSession().getServletContext().getRealPath("/");
					int lessonId = (Integer) work.get("lesson");
					workService.delWork(Integer.valueOf(workId));
					File file = new File(path + File.separatorChar + "upload"
							+ File.separatorChar + "lesson"
							+ File.separatorChar + lessonId % 15
							+ File.separatorChar + lessonId % 16
							+ File.separatorChar + lessonId
							+ File.separatorChar + workId + ".zip");
					//删除静态文件
					if (file.exists()) {
						file.delete();
						// 将zip包发送给app服务器
						FileUtil fileUtil = new FileUtil();
						byte[] by = new byte[1];
						fileUtil.httpPost(by, Integer.valueOf(workId),lessonId ,1);
					}
					
					result.put("code", 100);
					result.put("message", "删除成功");
				} else {
					result.put("code", 101);
					result.put("message", "此作业已被发布，不允许修改/删除");
				}
			} catch (Exception e) {
				result.put("code", 101);
				result.put("message", "此作业已被发布，不允许修改/删除");
				e.printStackTrace();
			}
		}
		return JSONObject.toJSONString(result);
	}
	
	@RequestMapping("/delWorkInfo")
	@ResponseBody
	@Transactional
	public Map<String,Object> delWorkInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		String workId = request.getParameter("workId");
		if (StringUtils.isEmpty(workId)) {
			result.put("code", 101);
			result.put("message", "参数异常");
		}else{
			try {
				if (!resultCardService.existResultCardListByWork(Integer.valueOf(workId))){
					mistakeService.delMistakeByWork(Integer.valueOf(workId));
					resultCardService.delResultCardInfoByWork(Integer.valueOf(workId));
					resultCardService.delResultCardByWork(Integer.valueOf(workId));
					
					Map<String, Object> work = workService.getWork(Integer
							.valueOf(workId));
					HttpSession session = request.getSession();
					String path = session.getServletContext().getRealPath("/");
					int lessonId = (Integer) work.get("lesson");
					workService.delWork(Integer.valueOf(workId));
					File file = new File(path + File.separatorChar + "upload"
							+ File.separatorChar + "lesson"
							+ File.separatorChar + lessonId % 15
							+ File.separatorChar + lessonId % 16
							+ File.separatorChar + lessonId
							+ File.separatorChar + workId + ".zip");
					//删除静态文件
					if (file.exists()) {
						file.delete();
					}
					Map<String, Object> lessonInfo = lessonService.getLessonInfo((Integer) work.get("lesson"));
					userService.setDelRecord((Integer)session.getAttribute("oaId"), (String)session.getAttribute("teacherName"),
							"删除"+lessonInfo.get("year")+lessonInfo.get("periodName")+lessonInfo.get("gradeName")+lessonInfo.get("subjectName")+lessonInfo.get("cateName")+"第"+lessonInfo.get("sortNo")+"课次"+work.get("categoryName")+"作业", 1);
					
					FileUtil fileUtil = new FileUtil();
					byte[] by = new byte[1];
					fileUtil.httpPost(by, Integer.valueOf(workId),lessonId ,1);
	//				workService.delWorkInfo(Integer.valueOf(workId));
	//				Work workBean = workService.getWorkBean(Integer.valueOf(workId));
	//				workBean.setModifyDate(new Date());
	//				workBean.setScore(0.0);
	//				workBean.setStandardTime(0);
	//				workBean.setZipState(0);
	//				workBean.setSourceSize(0l);
	//				workService.updateWork(workBean);
					result.put("code", 100);
					result.put("message", "删除成功");
				}else{
					result.put("code", 101);
					result.put("message", "作业已发布，不允许删除");
				}
			} catch (Exception e) {
				result.put("code", 101);
				result.put("message", "删除失败");
			}
		}
		return result;
	}

	@RequestMapping("/commitWorkInfo")
	@ResponseBody
	@Transactional
	public Map<String, Object> commitWorkInfo(HttpServletRequest request,
			HttpServletResponse response) {
		double score = 0;
		int standardTime = 0;
		Map<String, Object> result = new HashMap<String, Object>();
		String[] questions = request.getParameterValues("questions");
		String workId = request.getParameter("workId");
		try {
			if (!StringUtils.isEmpty(workId) && questions != null
					&& questions.length > 0) {
				if (resultCardService.existResultCardListByWork(Integer.valueOf(workId))) {
					result.put("code", 101);
					result.put("message", "此作业已被发布，不允许修改/删除");
					return result;
				}
				workService.delWorkInfo(Integer.valueOf(workId));
				Work work = workService.getWorkBean(Integer.valueOf(workId));
				List<String> questionList = Arrays.asList(questions);
				List<Map<String, String>> questionIds = new ArrayList<Map<String,String>>();
				for (Iterator<String> iterator = questionList.iterator(); iterator
						.hasNext();) {
					String questionId = (String) iterator.next();
					String sortNo = request.getParameter(questionId);
					Map<String, String> map = new HashMap<String, String>();
					map.put("sortNo", sortNo);
					map.put("questionId", questionId);
					questionIds.add(map);
				}
				Collections.sort(questionIds, new Comparator<Map<String,String>>() {
		            public int compare(Map<String,String> a, Map<String,String> b) {
		              int orderA = Integer.parseInt(a.get("sortNo"));
		              int orderB = Integer.parseInt(b.get("sortNo"));
		              return orderA - orderB;
		            }
				});
				List<WorkInfo> workInfoList = new ArrayList<WorkInfo>();
				int count = 0;
				for (Iterator<Map<String, String>> iterator = questionIds.iterator(); iterator
						.hasNext();) {
					Map<String, String> next = iterator.next();
					if (next == null) {
						continue;
					}
					String questionId = next.get("questionId");
					String sortNo = request.getParameter(questionId);
					//String appSortNoo = request.getParameter(questionId);
					Question question = questionService.getQuestion(Integer
							.valueOf(questionId));
					WorkInfo workInfo = new WorkInfo();
					workInfo.setCreateDate(new Date());
					workInfo.setModifyDate(new Date());
					workInfo.setSortNo(Integer.valueOf(sortNo));
					workInfo.setQuestion(question);
					workInfo.setWork(work);
					//String appSortNo = appSortNoo;
					if (question.getQuestionType() != 0) {
						count++;
						workInfo.setAppSortNo(Integer.toString(count));
					}
					workInfoList.add(workInfo);

					// 记录组卷总分和作答时间
					score = score + question.getScore();
					standardTime = standardTime + question.getStandardTime();

					List<Question> childQuestion = question.getChildQuestion();
					//int count = 1;
					if (question.getQuestionType() == 0
							&& childQuestion != null
							&& childQuestion.size() > 0) {
						for (Iterator<Question> iterator2 = childQuestion
								.iterator(); iterator2.hasNext();) {
							Question question2 = (Question) iterator2.next();
							WorkInfo workInfo2 = new WorkInfo();
							workInfo2.setCreateDate(new Date());
							workInfo2.setModifyDate(new Date());
							workInfo2.setQuestion(question2);
							workInfo2.setWork(work);
							count++;
							workInfo2.setAppSortNo(Integer.toString(count));
							workInfoList.add(workInfo2);
						}
					}
				}
				work.setWorkInfoList(workInfoList);
				work.setModifyDate(new Date());
				work.setScore(score);
				work.setStandardTime(standardTime);
				work.setZipState(0);
				if (!resultCardService.existResultCardListByWork(Integer.valueOf(workId))) {
					Map<String, Object> workMap = workService.getWork(Integer
							.valueOf(workId));
					lessonService.updateLesson((Integer) workMap.get("lesson"));
					result.put("code", 100);
					result.put("message", "作业修改成功");
				} else {
					result.put("code", 101);
					result.put("message", "此作业已被发布，不允许修改/删除");
				}
			}else if(!StringUtils.isEmpty(workId) && questions == null){
				if (resultCardService.existResultCardListByWork(Integer.valueOf(workId))) {
					result.put("code", 101);
					result.put("message", "此作业已被发布，不允许修改/删除");
					return result;
				}
				workService.delWorkInfo(Integer.valueOf(workId));
				Work work = workService.getWorkBean(Integer.valueOf(workId));
				work.setModifyDate(new Date());
				work.setScore(0.0);
				work.setStandardTime(0);
				work.setZipState(0);
				if (!resultCardService.existResultCardListByWork(Integer.valueOf(workId))) {
					Map<String, Object> workMap = workService.getWork(Integer
							.valueOf(workId));
					lessonService.updateLesson((Integer) workMap.get("lesson"));
					result.put("code", 100);
					result.put("message", "作业修改成功");
				} else {
					result.put("code", 101);
					result.put("message", "此作业已被发布，不允许修改/删除");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 101);
			result.put("message", "修改异常");
		}
		return result;
	}

	@RequestMapping("/publishWork")
	@ResponseBody
	@Transactional
	public Map<String, Object> publishWork(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] courses = request.getParameterValues("courses");
		String[] works = request.getParameterValues("works");
		String openTime = request.getParameter("openTime");
		if (courses == null || courses.length == 0 || works == null
				|| works.length == 0 || StringUtils.isEmpty(openTime)) {
			map.put("code", 101);
			map.put("message", "参数错误");
			return map;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date time = sdf.parse(openTime);
		List<String> courseList = Arrays.asList(courses);
		List<String> workList = Arrays.asList(works);

		//String path = request.getSession().getServletContext().getRealPath("/");
		String success = "发布成功";
		String message = "";
		A:for (Iterator<String> iterator2 = workList.iterator(); iterator2
				.hasNext();) {
			String workId = (String) iterator2.next();
//			Map<String, Object> work = workService.getWork(Integer.valueOf(workId));
			Work workBean = workService.getWorkBean(Integer.valueOf(workId));
			Date zipDate = workBean.getZipDate();
			Date modifyDate = workBean.getModifyDate();
			Integer zipState = workBean.getZipState();
//			Object object = work.get("zipDate");
//			Object object2 = work.get("modifyDate");
//			Object object3 = work.get("zipState");
			if (zipDate== null || modifyDate.getTime() != zipDate.getTime() || zipState!=2){
				message = message+workBean.getCategory().getCategoryName()+"打包尚未完成!	";
				continue A;
			}
			for (Iterator<String> iterator = courseList.iterator(); iterator
				.hasNext();) {
				String course = (String) iterator.next();
				List<Map<String, Object>> studentList = courseService
					.getStudentList(Integer.valueOf(course),true);
				for (Iterator<Map<String, Object>> iterator3 = studentList
						.iterator(); iterator3.hasNext();) {
					Map<String, Object> student = (Map<String, Object>) iterator3
							.next();
					// 判断答题卡是否已存在，已存在不重复布置作业
					ResultCard resultCard = resultCardService.getResultCard(
							Integer.valueOf(workId), (Integer) student.get("id"));
					if (resultCard == null) {
						boolean flag = resultCardService.publishWork(workBean,
								(Integer) student.get("id"), time);
						if (!flag) {
							message = message + workBean.getCategory().getCategoryName() + "没有组题!";
							continue A;
						}
					} else {
						resultCard.setOpenTime(time);
						resultCardService.updateResultCard(resultCard);
					}
				}
			}
		}

		map.put("code", 100);
		if (StringUtils.isEmpty(message)) {
			map.put("message", success);
		}else{
			map.put("message", message);
		}
		return map;
	}

	@RequestMapping("/removeWork")
	@ResponseBody
	@Transactional
	public Map<String, Object> removeWork(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		String[] courses = request.getParameterValues("courses");
		String[] works = request.getParameterValues("works");
		if (courses == null || courses.length == 0 || works == null
				|| works.length == 0) {
			map.put("code", 101);
			map.put("message", "参数错误");
			return map;
		}
		List<String> courseList = Arrays.asList(courses);
		List<String> workList = Arrays.asList(works);
		// 循环班级
		for (Iterator<String> iterator = courseList.iterator(); iterator
				.hasNext();) {
			String course = (String) iterator.next();
			List<Map<String, Object>> studentList = courseService
					.getStudentList(Integer.valueOf(course),false);
			
			// 循环作业
			for (Iterator<String> iterator2 = workList.iterator(); iterator2
					.hasNext();) {
				String work = (String) iterator2.next();
				
				Map<String, Object> courseMap = baseService.getCourse(Integer.valueOf(course));
				Map<String, Object> workMap = workService.getWork(Integer.valueOf(work));
				Map<String, Object> lessonInfo = lessonService.getLessonInfo((Integer) workMap.get("lesson"));
				userService.setDelRecord((Integer)session.getAttribute("oaId"), (String)session.getAttribute("teacherName"),
						"撤销"+courseMap.get("name")+"班"+"第"+lessonInfo.get("sortNo")+"课次"+workMap.get("categoryName")+"作业", 2);
				
				// 循环学生
				for (Iterator<Map<String, Object>> iterator3 = studentList
						.iterator(); iterator3.hasNext();) {
					Map<String, Object> student = (Map<String, Object>) iterator3
							.next();
					resultCardService.removeWork(Integer.valueOf(work),
							(Integer) student.get("id"));
				}
			}
		}
		map.put("code", 100);
		map.put("message", "作业撤回成功");
		return map;
	}

	@RequestMapping("/goPublishWork")
	public String goPulishWork(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		List<Map<String, Object>> cateList = baseService.cateList();
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> periodList = baseService.periodList();
		List<Map<String, Object>> deptList = baseService.depaList();

		modelMap.addAttribute("deptList", deptList);
		modelMap.addAttribute("periodList", periodList);
		modelMap.addAttribute("cateList", cateList);
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
		return "admin/work/publishWork";
	}

	@RequestMapping("/getCourseAndLesson")
	@ResponseBody
	public Map<String, Object> getCourseAndLesson(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String period = request.getParameter("period");
		String dept = request.getParameter("dept");
		String grade = request.getParameter("grade");
		String cate = request.getParameter("cate");
		String subject = request.getParameter("subject");
		List<Map<String, Object>> lessonList = null;
		List<Map<String, Object>> courseList = null;
		// 学期，年级，科目，层次都满足条件才查询对应的课次
		if (!StringUtils.isEmpty(period) && !StringUtils.isEmpty(dept)
				&& !StringUtils.isEmpty(grade) && !StringUtils.isEmpty(subject)
				&& !StringUtils.isEmpty(cate)) {
			lessonList = lessonService.getLessonList(period, grade, subject,
					cate);
			result.put("lessonList", lessonList);
		}
		if (!StringUtils.isEmpty(period) && !StringUtils.isEmpty(dept)
				&& !StringUtils.isEmpty(grade) && !StringUtils.isEmpty(subject)
				&& !StringUtils.isEmpty(cate)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("period", period);
			map.put("dept", dept);
			map.put("grade", grade);
			map.put("subject", subject);
			map.put("cate", cate);
			map.put("oaId", request.getSession().getAttribute("oaId"));
			courseList = baseService.courseList(map);
			result.put("courseList", courseList);
		}
		return result;
	}

	@RequestMapping("/getWorkList")
	@ResponseBody
	public Map<String, Object> getWorkList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String lessonId = request.getParameter("lessonId");
		if (StringUtils.isEmpty(lessonId)) {
			return result;
		}
		// 获取课次的作业列表
		List<Map<String, Object>> workList = workService.getWorks(
				Integer.valueOf(lessonId), 0);
		result.put("workList", workList);
		return result;
	}

	@RequestMapping("/scheduleClass")
	public String scheduleClass(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		List<Map<String, Object>> cateList = baseService.cateList();
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> periodList = baseService.periodList();
		List<Map<String, Object>> deptList = baseService.depaList();

		String cate = (String) ((Map<String, Object>)cateList.get(0)).get("code");
		String grade = (String) ((Map<String, Object>)gradeList.get(0)).get("code");
		String subject = (String) ((Map<String, Object>)subjectList.get(0)).get("code");
		String period = (String) ((Map<String, Object>)periodList.get(0)).get("id");
		String dept = (String) ((Map<String, Object>)deptList.get(0)).get("code");
		List<Map<String, Object>> lessonList = lessonService.getLessonList(period, grade, subject, cate);
		
		List<Map<String, Object>> courseList = new ArrayList<Map<String,Object>>();
		if (lessonList!=null && lessonList.size()>0) {
			int lessonId = (Integer) ((Map<String, Object>) lessonList.get(0))
					.get("iD");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodId", period);
			map.put("dept", dept);
			map.put("grade", grade);
			map.put("subject", subject);
			map.put("cate", cate);
			map.put("lessonId", lessonId);
			courseList = resultCardService.getClassScoreAndCorrect(map);
		}
		
		modelMap.addAttribute("deptList", deptList);
		modelMap.addAttribute("periodList", periodList);
		modelMap.addAttribute("cateList", cateList);
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
		modelMap.addAttribute("lessonList", lessonList);
		modelMap.addAttribute("courseList", courseList);
		return "statis/work/scheduleClass";
	}
	
	@RequestMapping("/getScheduleClassList")
	@ResponseBody
	public Map<String,Object> getScheduleClassList(HttpServletRequest request,HttpServletResponse response){
		String period = request.getParameter("period");
		String dept = request.getParameter("dept");
		String grade = request.getParameter("grade");
		String cate = request.getParameter("cate");
		String subject = request.getParameter("subject");
		String lessonId = request.getParameter("lesson");
		String className = request.getParameter("className");
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(dept)) {
			map.put("dept", dept);
		}
		if (!StringUtils.isEmpty(className)) {
			map.put("className", className);
		}
		map.put("periodId", period);
		map.put("grade", grade);
		map.put("subject", subject);
		map.put("cate", cate);
		boolean flag = true;
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> classScoreAndCorrect = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lessonList = lessonService.getLessonList(period, grade, subject, cate);
		if (lessonList!=null && lessonList.size()>0) {
			map.put("lessonId", lessonList.get(0).get("iD"));
			if (!StringUtils.isEmpty(lessonId)) {
				map.put("lessonId", Integer.parseInt(lessonId));
				flag = false;
			}
			classScoreAndCorrect = resultCardService.getClassScoreAndCorrect(map);
		}
		data.put("courseList", classScoreAndCorrect);
		data.put("condition", map);
		data.put("flag", flag);
		data.put("lessonList", lessonList);
		return data;
	}

	@RequestMapping("/scheduleClassDetail")
	public String scheduleClassDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {

		String courseId = request.getParameter("courseId");
		String lessonId = request.getParameter("lessonId");
		if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(lessonId)) {
			return "";
		}
		Map<String, Object> lessonInfo = lessonService.getLessonInfo(Integer.valueOf(lessonId));
		Map<String, Object> course = baseService.getCourse(Integer.valueOf(courseId));
		//获取班级下所有在读的学生
		List<Map<String, Object>> studentList = courseService.getStudentList(Integer.valueOf(courseId),true);
		for (Iterator<Map<String, Object>> iterator = studentList.iterator(); iterator.hasNext();) {
			Map<String, Object> student = (Map<String, Object>) iterator.next();
			int studentId = (Integer) student.get("id");
			List<Map<String, Object>> resultCardList = new ArrayList<Map<String,Object>>();		
			for (int i = 0; i < 5; i++) {
				Map<String,Object> map = new HashMap<String, Object>();
				resultCardList.add(map);
			}
			//获取学生在该课次下的所有答题卡
			List<Map<String, Object>> lessonResultCard = resultCardService.getLessonResultCard(studentId, Integer.valueOf(lessonId));
			for (Iterator<Map<String, Object>> iterator2 = lessonResultCard.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> resultCard = (Map<String, Object>) iterator2
						.next();
				int category = (Integer) resultCard.get("category");
				resultCardList.add(category-1, resultCard);
			}
			
			student.put("resultCardList", resultCardList.subList(0, 5));
		}
		modelMap.addAttribute("lesson", lessonInfo);
		modelMap.addAttribute("course", course);
		modelMap.addAttribute("studentList", studentList);
		return "statis/work/scheduleClassDetail";
	}

	@RequestMapping("/scheduleQuestionDetail")
	public String scheduleQuestionDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {

		String resultCardId = request.getParameter("resultCardId");
		String lessonId = request.getParameter("lessonId");
		String courseId = request.getParameter("courseId");
		String studentName = request.getParameter("studentName");
		List<Map<String, Object>> resultCardInfoList = resultCardService
				.getResultCardInfoList(Integer.valueOf(resultCardId));
		modelMap.addAttribute("resultCardInfoList", resultCardInfoList);
		modelMap.addAttribute("lessonId", lessonId);
		modelMap.addAttribute("courseId", courseId);
		modelMap.addAttribute("studentName", studentName);
		return "statis/work/scheduleQuestionDetail";
	}
	
	@RequestMapping("/scheduleQuestionDetail2")
	public String scheduleQuestionDetail2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {

		String resultCardId = request.getParameter("resultCardId");
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		String sortNo = request.getParameter("sortNo");
		String studentName = request.getParameter("studentName");
		List<Map<String, Object>> resultCardInfoList = resultCardService
				.getResultCardInfoList(Integer.valueOf(resultCardId));
		modelMap.addAttribute("resultCardInfoList", resultCardInfoList);
		modelMap.addAttribute("studentId", studentId);
		modelMap.addAttribute("courseId", courseId);
		modelMap.addAttribute("sortNo", sortNo);
		modelMap.addAttribute("studentName", studentName);
		return "statis/work/scheduleQuestionDetail2";
	}

	@RequestMapping("/scheduleStudent")
	public String scheduleStudent(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {

		return "statis/work/scheduleStudent";
	}

	@RequestMapping("/searchStudent")
	@ResponseBody
	public Map<String,Object> searchStudent(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		String studentName = request.getParameter("studentName");
		List<Map<String, Object>> studentList = new ArrayList<Map<String,Object>>();
		if (!StringUtils.isEmpty(studentName)) {
			studentList = courseService.searchStudent(studentName);
		}
		data.put("studentList", studentList);
		return data;
	}
	
	@RequestMapping("/scheduleStudentDetail")
	public String scheduleStudentDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		String studentName = request.getParameter("studentName");
		String sortNo = request.getParameter("sortNo");
		List<Map<String, Object>> lessonList = lessonService
				.getLessonListByCourse(Integer.valueOf(courseId));
		List<Map<String, Object>> resultCardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> lesson = null;
		if (lessonList != null && lessonList.size() > 0) {
			if (StringUtils.isEmpty(sortNo) || Integer.valueOf(sortNo) < 0
					|| Integer.valueOf(sortNo) > lessonList.size()) {
				lesson = lessonList.get(lessonList.size() - 1);
				resultCardList = resultCardService
						.getResultCardListByLandS(Integer.valueOf(studentId),
								(Integer) lesson.get("id"));
			} else if (Integer.valueOf(sortNo) > 0
					&& Integer.valueOf(sortNo) < lessonList.size()) {
				lesson = lessonList.get(Integer.valueOf(sortNo) - 1);
				resultCardList = resultCardService
						.getResultCardListByLandS(Integer.valueOf(studentId),
								(Integer) lesson.get("id"));
			}
		}
		Map<String, Object> course = baseService.getCourse(Integer.valueOf(courseId));
		//modelMap.addAttribute("lessonList", lessonList);
		modelMap.addAttribute("lesson", lesson);
		modelMap.addAttribute("sortNo", sortNo);
		modelMap.addAttribute("course", course);
		modelMap.addAttribute("studentName", studentName);
		modelMap.addAttribute("studentId", studentId);
		modelMap.addAttribute("resultCardList", resultCardList);
		return "statis/work/scheduleStudentDetail";
	}
	
	@RequestMapping("/zipWork")
	@ResponseBody
	public Map<String,Object> zipWork(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		String workId = request.getParameter("workId");
		if (StringUtils.isEmpty(workId)) {
			data.put("code", 101);
			data.put("message","参数错误");
			return data;
		}
		String path = request.getSession().getServletContext().getRealPath("/");
		Work workBean = workService.getWorkBean(Integer.valueOf(workId));
		Integer lessonId = workBean.getLesson().getiD();
		WorkThread workThread = new WorkThread(path,lessonId,workBean, workService);
		workThread.start();
		data.put("code", 100);
		data.put("message","组卷正在进行打包");
		return data;
	}
	
	@RequestMapping("/getWorkState")
	@ResponseBody
	public Map<String,Object> getWorkState(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		String lessonId = request.getParameter("lessonId");
		String courseId = request.getParameter("courseId");
		if (StringUtils.isEmpty(lessonId)||StringUtils.isEmpty(courseId)) {
			data.put("code", 101);
			data.put("message","参数错误");
		} else {
			List<Map<String, Object>> workList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> works = workService.getWorks(Integer.parseInt(lessonId), 0);
			A:for (Iterator<Map<String, Object>> iterator2 = works.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> work = (Map<String, Object>) iterator2.next();
				Map<String, Object> workObj = new HashMap<String, Object>();
				workObj.put("id", work.get("id"));
				workObj.put("category", work.get("category"));
				workObj.put("categoryName", work.get("categoryName"));
				Object object = work.get("zipDate");
				Object object2 = work.get("modifyDate");
				Object object3 = work.get("zipState");
				if (object== null || ((Date)object2).getTime() != ((Date) object).getTime() || (Integer)object3!=2){
					workObj.put("state", 0);
				}else {
					List<Map<String, Object>> studentList = courseService
							.getStudentList(Integer.valueOf(courseId),true);
					for (Iterator<Map<String, Object>> iterator3 = studentList
							.iterator(); iterator3.hasNext();) {
						Map<String, Object> student = (Map<String, Object>) iterator3
								.next();
						// 判断答题卡是否已存在，已存在不重复布置作业
						ResultCard resultCard = resultCardService.getResultCard(
								(Integer) work.get("id"), (Integer) student.get("id"));
						if (resultCard == null) {
							workObj.put("state", 1);
							workList.add(workObj);
							continue A;
						} else {
							workObj.put("state", 2);
						}
					}
				}
				workList.add(workObj);
			}
			// 获取题类列表
			List<Map<String, Object>> categorys = categoryService.getCategoryList();
			List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
			categoryList.addAll(categorys);
			for (Iterator<Map<String, Object>> iterator2 = categorys.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> category = (Map<String, Object>) iterator2
						.next();
				for (Iterator<Map<String, Object>> iterator = workList.iterator(); iterator
						.hasNext();) {
					Map<String, Object> work = (Map<String, Object>) iterator
							.next();
					if (category.get("id").equals(work.get("category"))) {
						categoryList.remove(category);
					}
				}
			}
			
			for (Iterator<Map<String, Object>> iterator = categoryList.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				Map<String, Object> workObj = new HashMap<String, Object>();
				workObj.put("id", 0);
				workObj.put("categoryName", map.get("categoryName"));
			    workObj.put("category", map.get("id"));
				workObj.put("state", 0);
				workList.add(workObj);
			}
			data.put("workList", workList);
			data.put("code", 100);
		}
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="workCorrectStatistics", method=RequestMethod.POST)
	public List<Map<String, Object>> workCorrectStatistics(int type, String subject, String period, String grade, String gradeId){
		return workService.workCorrectStatistics(type, subject, period, grade, gradeId);
	}
	
	@ResponseBody
	@RequestMapping(value="studentWorkCorrectStatis", method=RequestMethod.POST)
	public List<Map<String, Object>> studentWorkCorrectStatis(int courseId, String studentId){
		return workService.studentWorkCorrectStatis(courseId, studentId);
	}
	
	
}
