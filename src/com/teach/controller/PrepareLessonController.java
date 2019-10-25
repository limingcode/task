package com.teach.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.service.BaseService;
import com.skyedu.service.LessonService;
import com.teach.model.TpCourseware;
import com.teach.model.TpFileUpload;
import com.teach.service.FileUploadService;
import com.teach.service.PersonFileService;
import com.teach.service.PrepareLessonService;
import com.teach.service.TpCoursewareService;
import com.teach.service.TpFileTransferRecordService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;	

/**
 * 备课controller
 * @author skyedu_beyond
 * @Date 2017年7月19日
 * @version 1.0
 */
@Controller
@RequestMapping("/prepareLesson")
public class PrepareLessonController {
	
	@Resource
	private PrepareLessonService prepareLessonService;
	@Resource
	private BaseService baseService;
	@Resource
	private LessonService lessonService;
	@Resource
	private FileUploadService fileUploadService;
	@Resource
	private TpFileTransferRecordService recordService;
	@Resource
	private TpCoursewareService coursewareService;
	@Resource
	private PersonFileService personFileService;
	
	/**
	 * to备课层次选择
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/toPrepareLesson", method=RequestMethod.GET)
	public String toPrepareLesson(HttpServletRequest request, Model modelMap) {
		String oaId = String.valueOf(request.getSession().getAttribute("oaId"));
		modelMap.addAttribute("isTeacher", personFileService.checkIsTeacher(Integer.valueOf(oaId)));
		return "teach_v2/index";
	}
	
	/**
	 * to备课层次选择 V2
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/toHomePage", method=RequestMethod.GET)
	public String toHomePage(Model modelMap) {
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
		List<Map<String, Object>> cateList = baseService.cateList();
		List<Map<String, Object>> periodList = baseService.periodList();
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
		modelMap.addAttribute("cateList", cateList);
		modelMap.addAttribute("periodList", periodList);
//		return "teach/lesson/lessonLevel";
		return "teach_v2/homePage";
	}
	
	/**
	 * 通过科目、年级、层次、学期获取课次
	 * @param subject 科目
	 * @param grade 年级
	 * @param cate 层次
	 * @param period 学期
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getLesson", method=RequestMethod.POST)
	public String getLesson(String subject, String grade, String cate, String period) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> lessonList = lessonService.getLessonList(period, grade, subject, cate);
		map.put("lessonList", lessonList);
		if(lessonList != null && lessonList.size() > 0) {
			List<Map<String, Object>> lessonStatus = prepareLessonService.getLessonStatus(period, grade, subject, cate);
			map.put("lessonStatus", lessonStatus);
		}
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}

	/**
	 * 上传说课、实录视频，如以上传过视频则直接跳到播放视频页面
	 * @param lessonId
	 * @param type
	 * @param isUpdate
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toUploadFile", method=RequestMethod.GET)
	public String toUploadFile(int lessonId, String type, String isUpdate, Model model) {
		byte fileType = 6;
		if("2".equals(type)) {//说课视频
			fileType = 5;
		} 
		TpFileUpload file = fileUploadService.getFileByLessonIdAndFileType(lessonId, fileType);
		if(StringUtils.equals("true", isUpdate) || file == null) {
			TpCourseware courseware = coursewareService.getCoursewareByLessonId(lessonId);
			if (courseware != null) {
				String url = "";
				if (fileType == 5) {
					url = courseware.getLessonVideoUrl();
				} else {
					url = courseware.getMemoirVideoUrl();
				}
				model.addAttribute("url", url);
			}
			return "teach/courseware/uploadFile";
		}
		model.addAttribute("file", file);
		return "teach/courseware/previewVideo";
	}
	
	/**
	 * 上传说课、实录视频，如以上传过视频则直接跳到播放视频页面
	 * @param lessonId
	 * @param type
	 * @param isUpdate
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toUploadFileV2", method=RequestMethod.GET)
	public String toUploadFileV2(int lessonId, String type, Model model) {
		byte fileType = 6;
		if("2".equals(type)) {//说课视频
			fileType = 5;
		} 
		TpFileUpload file = fileUploadService.getFileByLessonIdAndFileType(lessonId, fileType);
		if(file == null) {
			TpCourseware courseware = coursewareService.getCoursewareByLessonId(lessonId);
			if (courseware != null) {
				String url = "";
				if (fileType == 5) {
					url = courseware.getLessonVideoUrl();
				} else {
					url = courseware.getMemoirVideoUrl();
				}
				model.addAttribute("url", url);
			}
		} else {
			model.addAttribute("file", file);
		}
		return "teach_v2/videoUpload";
	}
	
	/**
	 * 获取文件同步状态 
	 * @param lessonId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getFileSynchStatus", method=RequestMethod.POST)
	public String getFileSynchStatus(int lessonId) {
		List<Map<String, Object>> list = recordService.getListByLessonId(lessonId);
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}

}
