package com.skyedu.controller.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.service.MessageService;

@Controller
@RequestMapping("/appMessage")
public class AppMessageController {

	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value="/getMessageList")
	@ResponseBody
	public Map<String,Object> getMessageList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		String studentId = request.getParameter("userStudentId");
		String pageNoo = request.getParameter("pageNo");
		if (studentId==null) {
			map.put("code", 101);
			map.put("message", "参数错误");
		}else{
			String state = request.getParameter("state");
			if (state==null) {
				state = "0";
			}
			int pageNo = 1;
			if (pageNoo!=null && !"0".equals(pageNoo)) {
				pageNo= Integer.parseInt(pageNoo);
			}
			List<Map<String, Object>> messageList = messageService.getMessageList(Integer.valueOf(studentId), Integer.valueOf(state), pageNo);
			for (Iterator<Map<String, Object>> iterator = messageList.iterator(); iterator.hasNext();) {
				Map<String, Object> message = (Map<String, Object>) iterator
						.next();
				messageService.updateMessage((Integer)message.get("iD"));
			}
			int count = messageService.getUnreadCount(Integer.valueOf(studentId));
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("messageList", messageList);
			data.put("count", count);
			map.put("code", 100);
			map.put("message", "成功");
			map.put("data", data);
		}
		return map;
	}
	
	@RequestMapping(value="/delMessage")
	@ResponseBody
	public Map<String,Object> delMessage(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = request.getParameter("messageId");
			if (id == null) {
				map.put("code", 101);
				map.put("message", "参数错误");
			}else{
				messageService.delMessage(Integer.valueOf(id));
				map.put("code", 100);
				map.put("message", "删除成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("code", 101);
			map.put("message", "删除失败");
		}
		return map;
	}
	
	@RequestMapping(value="/updateMessage")
	@ResponseBody
	public Map<String,Object> updateMessage(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String id = request.getParameter("messageId");
			if (id == null) {
				map.put("code", 101);
				map.put("message", "参数错误");
			}else{
				messageService.updateMessage(Integer.valueOf(id));
				map.put("code", 100);
				map.put("message", "更新成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("code", 101);
			map.put("message", "更新失败");
		}
		return map;
	}
}
