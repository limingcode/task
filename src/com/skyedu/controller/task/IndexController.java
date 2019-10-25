/*package com.skyedu.controller.task;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skyedu.service.FileService;
import com.skyedu.service.WeChatService;

*//**
 * 首页Controller 微信授权处理
 * @author xj.chen
 * @date 2015-10-13
 *//*
@Controller("taskIndexController")
@RequestMapping("/index")
public class IndexController extends BaseController {

	@Resource
	private WeChatService weChatService;
	@Resource
	private FileService fileService;
	
	*//**
	 * 网页授权
	 * @param code
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/authorization", method = RequestMethod.GET)
	public String authorization(String redirectUrl, HttpServletRequest request, ModelMap model) {
		model.put("method", "get");
		model.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize");
		model.addAttribute("parameterMap", weChatService.getAuthorizeParameterMap(redirectUrl));
		return "/wx/authorization";
	}
	
	*//**
	 * 授权同意后，获取用户信息，并转至首页
	 * @param code
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(String code, String redirectUrl, HttpServletRequest request, ModelMap model) {
		if(null != redirectUrl && redirectUrl.indexOf("oa/user/vote.jhtml")>=0){//投票系统临时更改
			AccessToken accessToken = weChatService.accessToken(code);
			if (accessToken == null) {
				return ERROR_VIEW;
			}else{
				request.getSession().setAttribute("wx_openid_vote",accessToken.getOpenid());
				return "redirect:" + redirectUrl;
			}
		}
		Employee employee = (Employee) request.getSession().getAttribute(TaskInterceptor.LOGIN_STUDENT);
		if (employee != null) {
			if(employee.getStatus()==-1){
				model.put("message", Message.warn("很遗憾的通知！您已离开“蓝天”，功能已被禁用。", null));
				return NO_ACCESS_VIEW;
			}
			if (redirectUrl != null) {
				if(redirectUrl.indexOf("?")>0||redirectUrl.indexOf("%26")>0)
					redirectUrl += "&";
				else
					redirectUrl += "?";
				return "redirect:" + redirectUrl + "oaId=" + employee.getOaId();
			}
			return "redirect:/wx/user/index.jhtml?wx_openid=" + employee.getWxOpenid();
		}
		try {
			// 授权后access_token
			AccessToken accessToken = weChatService.accessToken(code);
			if (accessToken == null) {
				return ERROR_VIEW;
			}
			employee = employeeService.findByOpenid(accessToken.getOpenid());
			if (employee == null) {
				model.put("message", Message.warn("对不起，本功能仅对内部员工开放。", null));
				return NO_ACCESS_VIEW;
			}
			if(employee.getStatus()==-1){
				model.put("message", Message.warn("很遗憾的通知！您已离开“蓝天”，功能已被禁用。", null));
				return NO_ACCESS_VIEW;
			}
			//拉取用户信息
			UserInfo userInfo = weChatService.userinfo(accessToken.getAccessToken(), accessToken.getOpenid());
			if (userInfo != null && userInfo.getHeadimgurl() != null && employee.getPortrait() == null) {
				// 获取微信头像更新至本地
				String portrait = fileService.download(FileType.image, userInfo.getHeadimgurl());
				employeeService.updatePortrait(employee.getId(), portrait);
			}
			// 初始化session
			request.getSession().setAttribute(TaskInterceptor.LOGIN_STUDENT, employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (redirectUrl != null) {
			if(redirectUrl.indexOf("?")>0||redirectUrl.indexOf("%26")>0)
				redirectUrl += "&";
			else
				redirectUrl += "?";
			return "redirect:" + redirectUrl + "oaId=" + employee.getOaId();
		}
		return "redirect:/wx/user/index.jhtml?wx_openid=";// + employee.getWxOpenid();
	}

}
*/