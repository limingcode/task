package com.skyedu.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.model.Message;
import com.skyedu.model.MessageS;
import com.skyedu.service.BaseService;
import com.skyedu.service.CourseService;
import com.skyedu.service.MessageService;
import com.util.DateUtil;
import com.util.JpushClientUtil;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private BaseService baseService;
	
	/**
	 * 发布信息
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping("/publishMessage")
	@ResponseBody
	@Transactional
	public Map<String,Object> publishMessage(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String, Object>();
		String[] courses = request.getParameterValues("courses");
		String message = request.getParameter("message");
		String title = request.getParameter("title");
		String openTime = request.getParameter("openTime");
		int oaId = (Integer) request.getSession().getAttribute("oaId");
		String uuid = UUID.randomUUID().toString().replace("-", "");
		try {
			if (courses!=null && courses.length>0) {
				List<String> courseList = Arrays.asList(courses);
				for (Iterator<String> iterator = courseList.iterator(); iterator.hasNext();) {
					String course = (String) iterator.next();
					//推送消息
					Message mess = new Message();
					mess.setCourse(Integer.valueOf(course));
					mess.setCreateDate(new Date());
					mess.setMessage(message);
					mess.setSender(oaId);
					mess.setTitle(title);
					mess.setUuid(uuid);
					if (StringUtils.isEmpty(openTime)) {
						mess.setOpenTime(new Date());
						mess.setStatus(1);
						//保存信息
						messageService.saveMessage(mess);
						//循环遍历学生推送信息
						List<Map<String, Object>> studentList = courseService.getStudentList(Integer.valueOf(course),true);
						for (Iterator<Map<String, Object>> iterator2 = studentList.iterator(); iterator2
								.hasNext();) {
							Map<String, Object> student = (Map<String, Object>) iterator2
									.next();
							MessageS messageS = new MessageS();
							messageS.setMessage(mess.getiD());
							messageS.setStudent((Integer) student.get("id"));
							messageS.setState(0);
							messageService.saveMessageS(messageS);
							//向app推送消息
							//JpushClientUtil.sendToAliasIos(student.get("id").toString(), title, title, message, "");
						}
						//向app推送消息
						JpushClientUtil.sendToTagsIos("c_"+course, title, title, message, "");
					} else {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
						Date time = sdf.parse(openTime);
						mess.setOpenTime(time);
						mess.setStatus(0);
						//保存信息，信息推送由定时任务完成
						messageService.saveMessage(mess);
					}
				}
				map.put("message", "消息推送成功");
				map.put("code", 100);
			}else{
				map.put("message", "消息推送失败");
				map.put("code", 101);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("message", "消息推送异常");
			map.put("code", 101);
		}
		
		return map;
	}
	
	/**
	 * 消息列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/messageList")
	public String messageList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		Map<String, Object> condition = new HashMap<String, Object>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String pageNo = request.getParameter("pageNo");
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);
		condition.put("pageNo", pageNo);
		List<Map<String, Object>> messageList = messageService.getMessageList(condition);
		modelMap.put("messageList", messageList);
		modelMap.put("condition", condition);
		return "admin/message/messageList";
	}

	
	/**
	 * 前往消息发布页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/goPublishMessage")
	public String goPublishMessage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
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
		return "admin/message/publishMessage";
	}
	
	
}
