package com.skyedu.controller.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.exception.QuestionException;
import com.skyedu.model.Hierarchy;
import com.skyedu.model.Question;
import com.skyedu.model.Result;
import com.skyedu.model.Work;
import com.skyedu.model.WorkInfo;
import com.skyedu.service.BaseService;
import com.skyedu.service.HierarchyService;
import com.skyedu.service.LessonService;
import com.skyedu.service.QuestionService;
import com.skyedu.service.UserService;
import com.skyedu.service.WorkService;
import com.util.CommonUtil;
import com.util.DigestUtils;
import com.util.HttpClientUtils;
import com.util.WorkThread;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/question")
public class QuestionController {


	@Autowired
	private HierarchyService hierarchyService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private WorkService workService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/input")
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		return "admin/question/question";
	}
	
	@RequestMapping("/inputQuestion")
	public String inputQuestion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String grade = request.getParameter("grade");
		String cate = request.getParameter("cate");
		String subject = request.getParameter("subject");
		condition.put("grade", grade);
		condition.put("cate", cate);
		condition.put("subject", subject);
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> cateList = baseService.cateList();
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
		modelMap.addAttribute("cateList", cateList);
		modelMap.addAttribute("condition", condition);
		return "admin/question/input/questionEditor";
	}
	
	@RequestMapping("/editorQuestion")
	public String editorQuestion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> condition = new HashMap<String, Object>();
		List<Map<String, Object>> questionList = new ArrayList<Map<String,Object>>();
		String questionId = request.getParameter("questionId");
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> cateList = baseService.cateList();
		if (questionId!=null) {
			Map<String, Object> questionHtml = questionService.getQuestionHtml(Integer.valueOf(questionId));
			Question question = questionService.getQuestion(Integer.valueOf(questionId));
			questionHtml.put("brief", question.getBrief());
			Hierarchy hierarchy = question.getHierarchy();
			condition.put("grade", hierarchy.getGrade());
			condition.put("cate", hierarchy.getCate());
			condition.put("subject", hierarchy.getSubject());
			List<Question> childQuestion = question.getChildQuestion();
			questionList.add(questionHtml);
			for (Iterator<Question> iterator = childQuestion.iterator(); iterator
					.hasNext();) {
				Question question2 = (Question) iterator.next();
				Map<String, Object> questionHtml2 = questionService.getQuestionHtml(question2.getiD());
				questionHtml2.put("brief", question2.getBrief());
				questionList.add(questionHtml2);
			}
		}
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
		modelMap.addAttribute("cateList", cateList);
		modelMap.addAttribute("condition", condition);
		modelMap.addAttribute("questionList", questionList);
		return "admin/question/editor/questionAlter";

	}

	@RequestMapping("/questionList")
	public String questionList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String title = request.getParameter("title");
		String grade = request.getParameter("grade");
		String cate = request.getParameter("cate");
		String subject = request.getParameter("subject");
		String pageNoo = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoo != null && Integer.valueOf(pageNoo) > 0) {
			pageNo = Integer.valueOf(pageNoo);
		}
		condition.put("grade", grade);
		condition.put("cate", cate);
		condition.put("subject", subject);
		condition.put("pageNo", pageNo);
		condition.put("title", title);
		List<Map<String, Object>> quesList = questionService
				.quesList(condition);
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> cateList = baseService.cateList();
		modelMap.addAttribute("cateList", cateList);
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
		modelMap.addAttribute("quesList", quesList);
		modelMap.addAttribute("condition", condition);
		return "admin/question/list/questionList";
	}

	@RequestMapping("/selectQuestion")
	public String selectQuestion(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		String workId = request.getParameter("workId");
		String brief = request.getParameter("brief");
		Map<String, Object> work = workService.getWork(Integer.valueOf(workId));
		List<Map<String, Object>> questionList = questionService
				.getQuestionByWork(Integer.valueOf(workId),brief);
		modelMap.addAttribute("questionList", questionList);
		modelMap.addAttribute("work", work);
		return "admin/question/list/selectQuestion";
	}

	@RequestMapping("/commitQuestion")
	@ResponseBody
	public Map<String, Object> commitQuestion(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		String appPath = request.getSession().getServletContext()
				.getRealPath("/");
		String quesPath = appPath + File.separatorChar + "upload"
				+ File.separatorChar + "question" + File.separatorChar;
		String quesOriPath = appPath + File.separatorChar + "upload"
				+ File.separatorChar + "questionOri" + File.separatorChar;
		String qItemList = request.getParameter("qItemList");
		List<Map<String, Object>> parse = (List<Map<String, Object>>) JSONObject
				.parse(qItemList);
		String grade = request.getParameter("grade");
		String subject = request.getParameter("subject");
		String cate = request.getParameter("cate");
		Hierarchy hierarchy = hierarchyService.getHierarchy(grade, subject,
				cate);
		int oaId = (Integer)request.getSession().getAttribute("oaId");
		return questionService.saveQuestions(parse, oaId, hierarchy, appPath, quesPath, quesOriPath);
	}
	
	@RequestMapping("/commitQuestionEx")
	@ResponseBody
	public Map<String, Object> commitQuestionEx(@RequestParam(required=true)String grade,@RequestParam(required=true)String subject,@RequestParam(required=true)String cate,@RequestParam(required=true)String parentId,HttpServletRequest request,
			HttpServletResponse response) {
		String appPath = request.getSession().getServletContext()
				.getRealPath("/");
		String quesPath = appPath + File.separatorChar + "upload"
				+ File.separatorChar + "question" + File.separatorChar;
		String quesOriPath = appPath + File.separatorChar + "upload"
				+ File.separatorChar + "questionOri" + File.separatorChar;
		String quesOriTempPath = appPath + File.separatorChar + "upload"
				+ File.separatorChar + "questionOriTemp" + File.separatorChar;
		String qItemList = request.getParameter("qItemList");
		List<Map<String, Object>> parse = (List<Map<String, Object>>) JSONObject
				.parse(qItemList);
		return questionService.saveQuestionsEx(request, appPath, quesPath, quesOriPath, quesOriTempPath, parse, grade, subject, cate,
				parentId);
	}

	

	@RequestMapping("/delQuestion")
	@ResponseBody
	@Transactional
	public Map<String,Object> delQuestion(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		String questionId = request.getParameter("questionId");
		HttpSession session = request.getSession();
		Question question = questionService.getQuestion(Integer.valueOf(questionId));
		userService.setDelRecord((Integer)session.getAttribute("oaId"), (String)session.getAttribute("teacherName"),
				"删除id="+questionId+"标题"+question.getBrief(), -1);
		try {
			boolean flag = questionService.delQuestion(Integer.valueOf(questionId));
			if (flag) {
				String path = session.getServletContext().getRealPath("/");
				File file = new File(path + File.separatorChar + "upload"
						+ File.separatorChar + "question"
						+ File.separatorChar + Integer.valueOf(questionId) % 15
						+ File.separatorChar + Integer.valueOf(questionId) % 16
						+ File.separatorChar + Integer.valueOf(questionId));
				//删除静态文件夹
				if (file.exists()) {
					FileUtils.deleteQuietly(file);
				}
				map.put("code", 100);
				map.put("message", "题目删除成功");
			}else{
				map.put("code", 101);
				map.put("message", "题目删除失败，已被引用");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 101);
			map.put("message", "题目删除失败，数据异常");
		}
		
		return map;
	}
	
	@RequestMapping("/preview")
	public String preview(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		int questionId = Integer.valueOf(request.getParameter("questionId"));
		List<Map<String,Object>> questionList = new ArrayList<Map<String,Object>>();
		Question question = questionService.getQuestion(questionId);
		Map<String, Object> questionToMap = questionToMap(question);
		List<Question> childQuestions = question.getChildQuestion();
		List<Map<String,Object>> childQuestion = new ArrayList<Map<String,Object>>();
		
		String voiceUrl="";
		String waitTime="";
		List<Map<String, Object>> voiceList = (List<Map<String, Object>>) questionToMap.get("voice");
		Integer qId = (Integer)questionToMap.get("iD");
		questionToMap.put("voiceFlag1", true);
		for (Iterator<Map<String, Object>> iterator = voiceList.iterator(); iterator
				.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator
					.next();
			voiceUrl = voiceUrl + File.separatorChar+"task"+File.separatorChar+"upload"+File.separatorChar+"question"+File.separatorChar+qId%15+File.separatorChar+qId%16+File.separatorChar+qId+File.separatorChar+map.get("name")+",";
			waitTime = waitTime + map.get("wait")+",";
			
		}
		
		for (Iterator<Question> iterator = childQuestions.iterator(); iterator.hasNext();) {
			Question question2 = (Question) iterator.next();
			Map<String, Object> questionToMap2 = questionToMap(question2);
			if ((Boolean) questionToMap2.get("voiceFlag")) {
				questionToMap.put("voiceFlag", true);
			}
			if ((Integer)questionToMap2.get("questionType")==5) {
				questionToMap.put("voiceFlag1", false);
			}
			childQuestion.add(questionToMap2);
			List<Map<String, Object>> voiceList2 = (List<Map<String, Object>>) questionToMap2.get("voice");
			Integer qId2 = (Integer)questionToMap2.get("iD");
			for (Iterator<Map<String, Object>> iterator2 = voiceList2.iterator(); iterator2
					.hasNext();) {
				Map<String, Object> map2 = (Map<String, Object>) iterator2
						.next();
				voiceUrl = voiceUrl + File.separatorChar+"task"+File.separatorChar+"upload"+File.separatorChar+"question"+File.separatorChar+qId2%15+File.separatorChar+qId2%16+File.separatorChar+qId2+File.separatorChar+map2.get("name")+",";
				waitTime = waitTime + map2.get("wait")+",";
			}
		}
		questionToMap.put("childQuestion", childQuestion);
		questionToMap.put("voiceUrl", voiceUrl);
		questionToMap.put("waitTime", waitTime);
		questionList.add(questionToMap);
		modelMap.put("voiceUrl", voiceUrl);
		modelMap.put("waitTime", waitTime);
		modelMap.put("questionList", questionList);
		modelMap.put("config", new CommonUtil().getConfig());
		return "admin/question/list/preview";
	}
	
	private Map<String,Object> questionToMap(Question question){
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("iD", question.getiD());
		data.put("analyzation", question.getAnalyzation());
		data.put("answer", question.getAnswer());
		data.put("brief", question.getBrief());
		data.put("standardTime", question.getStandardTime());
		String voice = question.getVoice();
		List<Map<String,Object>> vList = (List<Map<String, Object>>) JSONObject.parse(voice);
		data.put("voice", vList);
		data.put("isVoice", question.getIsVoice());
		if (question.getIsVoice()==0 ) {
			data.put("voiceFlag", false);
		}else if(question.getIsVoice()==1){
			data.put("voiceFlag", true);
		}
		data.put("modifyDate", question.getModifyDate());
		data.put("questionType", question.getQuestionType());
		data.put("resultList", question.getResultList());
		data.put("score", question.getScore());
		String title = question.getTitle();
		String content = title.replace("<@T", "<i class='anwer'").replace("<@/T>", "</i>").replace("\\n", "<br>");
		
		Pattern p = Pattern.compile("  ");
		Matcher matcher = p.matcher(content);
		while(matcher.find()){
			content = content.replace("  ","&nbsp;"+" ");
		}
		
		List<Map<String,Object>> pList = (List<Map<String, Object>>) JSONObject.parse(content);
		for (Iterator<Map<String, Object>> iterator = pList.iterator(); iterator.hasNext();) {
			Map<String, Object> pModule = (Map<String, Object>) iterator.next();
			List<Map<String, Object>> imgs = (List<Map<String, Object>>) pModule
					.get("imgs");
			Map<String, Object> style = (Map<String, Object>) pModule
					.get("style");
		}
		data.put("title", pList);
		return data;
	}
	
	@RequestMapping("/previewWork")
	public String previewWork(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		int workId = Integer.valueOf(request.getParameter("workId"));
		Map<String, Object> work = workService.getWork(workId);
		List<WorkInfo> workInfoList = workService.getWorkInfoList(workId, false);
		List<Map<String,Object>> questionList = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < workInfoList.size(); i++) {
			WorkInfo workInfo = workInfoList.get(i);
			Question question = workInfo.getQuestion();
			Map<String, Object> questionToMap = questionToMap(question);
			List<Question> childQuestions = question.getChildQuestion();
			List<Map<String,Object>> childQuestion = new ArrayList<Map<String,Object>>();
			
			String voiceUrl="";
			String waitTime="";
			List<Map<String, Object>> voiceList = (List<Map<String, Object>>) questionToMap.get("voice");
			Integer qId = (Integer)questionToMap.get("iD");
			questionToMap.put("voiceFlag1", true);
			for (Iterator<Map<String, Object>> iterator = voiceList.iterator(); iterator
					.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator
						.next();
				voiceUrl = voiceUrl + File.separatorChar+"task"+File.separatorChar+"upload"+File.separatorChar+"question"+File.separatorChar+qId%15+File.separatorChar+qId%16+File.separatorChar+qId+File.separatorChar+map.get("name")+",";
				waitTime = waitTime + map.get("wait")+",";
				
			}
			
			for (Iterator<Question> iterator = childQuestions.iterator(); iterator.hasNext();) {
				Question question2 = (Question) iterator.next();
				Map<String, Object> questionToMap2 = questionToMap(question2);
				if ((Boolean) questionToMap2.get("voiceFlag")) {
					questionToMap.put("voiceFlag", true);
				}
				if ((Integer)questionToMap2.get("questionType")==5) {
					questionToMap.put("voiceFlag1", false);
				}
				childQuestion.add(questionToMap2);
				List<Map<String, Object>> voiceList2 = (List<Map<String, Object>>) questionToMap2.get("voice");
				Integer qId2 = (Integer)questionToMap2.get("iD");
				for (Iterator<Map<String, Object>> iterator2 = voiceList2.iterator(); iterator2
						.hasNext();) {
					Map<String, Object> map2 = (Map<String, Object>) iterator2
							.next();
					voiceUrl = voiceUrl + File.separatorChar+"task"+File.separatorChar+"upload"+File.separatorChar+"question"+File.separatorChar+qId2%15+File.separatorChar+qId2%16+File.separatorChar+qId2+File.separatorChar+map2.get("name")+",";
					waitTime = waitTime + map2.get("wait")+",";
					
				}
			}
			questionToMap.put("childQuestion", childQuestion);
			questionToMap.put("voiceUrl", voiceUrl);
			questionToMap.put("waitTime", waitTime);
			questionList.add(questionToMap);
		}
		
		modelMap.put("work", work);
		modelMap.put("questionList", questionList);
		modelMap.put("config", new CommonUtil().getConfig());
		return "admin/question/list/preview";
	}
	
	public String rex(String content){
		Pattern p = Pattern.compile("[0-9]+");
		Matcher matcher = p.matcher(content);
		int index = content.length();
		matcher.region(index-6, index);
		String num = "1";
		while(matcher.find()){
			num = matcher.group();
		}
		return num;
	}
	
	/**
     * 不按照比例，指定大小进行缩放
     * 
     * @throws IOException
     */
    private void changeSize(String oldPath,String newPath,int width,int height) throws IOException {
        /**
         * keepAspectRatio(false) 默认是按照比例缩放的
         */
        Thumbnails.of(oldPath).size(width*2, height*2).keepAspectRatio(false).toFile(newPath);
    }
	
	public static void main(String[] args) {
		QuestionController questionController = new QuestionController();
		String s = questionController.rex("2017秋季英语六年级尖子A第9课Speaking（16）");
		System.out.println(s);
	}
}