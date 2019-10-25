/*package com.skyedu.controller.task;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyedu.Message;
import com.skyedu.dao.impl.TeacherDAO;
import com.skyedu.service.WeChatService;

*//**
 * 文件Controller
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-24
 *//*
@Controller("taskFileController")
@RequestMapping("/file")
public class FileController extends BaseController{

	@Resource 
	private WeChatService weChatService;

	@Resource 
	private TeacherDAO employeeDAO;
	
	*//**
	 * 上传
	 * @param mediaId
	 * @param request
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> upload(String mediaId, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = weChatService.mediaDownload(mediaId);
		map.put("message", Message.error("上传失败", null));
		if (url != null) {
			// Do something
			map.put("message", Message.success("上传成功", null));
		}
		map.put("url", url);
		return map;
	}
	
}
*/