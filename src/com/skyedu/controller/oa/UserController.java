/*package com.skyedu.controller.oa;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.Message;
import com.skyedu.controller.task.BaseController;
import com.skyedu.dao.impl.TeacherDAO;
import com.skyedu.service.MessageService;

@Controller("oaUserController")
@RequestMapping("/oa/user")
*//**
 * oa系统控制器
 * @author xj.chen
 *
 *//*
public class UserController extends BaseController {

	@Resource private TeacherDAO em;
	@Resource private MessageService msg;
	
	private static final String RETURN_FLAG = "/oa/user/";
	
	*//**
	 * 向某app发送消息(待重写)
	 * @param model
	 * @param request
	 * @param type
	 * @param toUrl
	 * @param toOaId
	 * @param byOaId
	 * @param first
	 * @param remark
	 * @param keyword
	 * @return
	 *//*
	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public Message sendMsg(ModelMap model, HttpServletRequest request,
			int type, String toUrl, int toOaId, int byOaId, String first, String remark,
			String[] keyword){
		if(true){
			return SUCCESS;
		}
		return ERROR;
	}
}
*/