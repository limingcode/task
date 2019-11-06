package com.skyedu.controller.admin;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skyedu.Message;

/**
 * Controller的基类 后台控制器
 * @author xj.chen
 * @date 2014-8-22
 */

public class BaseController {

	/** 错误页面 */
	protected static final String ERROR_VIEW = "/admin/common/error";
	/** 成功消息码 */
	protected static final Message SUCCESS = Message.success("admin.message.success", null);
	/** 失败消息码 */
	protected static final Message ERROR = Message.error("admin.message.error", null);
	/**
	 * 页面提示
	 * @param redirectAttributes
	 * @param message
	 */
	protected void flassMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null){
			redirectAttributes.addFlashAttribute("message", message);
		}
			
	}
}
