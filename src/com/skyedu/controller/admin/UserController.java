package com.skyedu.controller.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skyedu.model.WaTeacherLoginInfo;
import com.skyedu.service.BaseService;
import com.skyedu.service.LoginLogService;
import com.skyedu.service.RoleService;
import com.skyedu.service.UserService;
import com.util.DigestUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private BaseService baseService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Resource
	private LoginLogService loginLogService;
	
	@RequestMapping(value = "/prepareLessons")
	public String prepareLessons(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
//		return "prepareLessons";
		return "prepareLessonsV2";
	}
	
	@RequestMapping(value = "/index")
	public String index(@ModelAttribute("message") String message, Model model) {
		model.addAttribute("message", message);
		return "../../index";
	}
	
	@RequestMapping(value = "/statis")
	public String statis(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		return "/statis/index";
	}
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,
			HttpServletResponse response,final RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)) {
			Object oaId = request.getParameter("oaId");
			if (oaId==null) {
				Object attribute = session.getAttribute("oaId");
				if (attribute==null) {
					redirectAttributes.addFlashAttribute("message", "账号或密码错误");
					return "redirect:index.jhtml";
				}
			} else {
				Map<String, Object> teacher = baseService.teacherInfo(Integer.parseInt((String)oaId));
				if (teacher==null) {
					redirectAttributes.addFlashAttribute("message", "账号或密码错误");
					return "redirect:index.jhtml";
				}
				List<String> userRoleList = roleService.getUserRoleList(Integer.parseInt((String)oaId));
				session.setAttribute("userRoleUrls", userRoleList);
				session.setAttribute("oaId", oaId);
				session.setAttribute("teacherName", teacher.get("name"));
			}
		} else {
			List<Map<String, Object>> users = userService.getOaId(username);
			for (Iterator<Map<String, Object>> iterator = users.iterator(); iterator.hasNext();) {
				Map<String, Object> user = (Map<String, Object>) iterator.next();
				String dbpassword = (String)user.get("password");
				String seed = (String)user.get("seed");
				if (DigestUtils.isEmpty(password, seed, dbpassword)) {
					int oaId = (Integer) user.get("oaId");
					List<String> userRoleList = roleService.getUserRoleList(oaId);
					session.setAttribute("userRoleUrls", userRoleList);
					session.setAttribute("oaId", oaId);
					session.setAttribute("teacherName", user.get("username"));
//					return "prepareLessons";
					WaTeacherLoginInfo waTeacherLoginInfo=new WaTeacherLoginInfo( oaId, new Date());
					loginLogService.add(waTeacherLoginInfo);
					return "prepareLessonsV2";
				}
				
			}
			redirectAttributes.addFlashAttribute("message", "账号或密码错误");
			return "redirect:index.jhtml";
		}
//		return "prepareLessons";
		return "prepareLessonsV2";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
			request.getSession().invalidate();
			return "redirect:/index.jsp";
	}
	
	@RequestMapping("/bugLog")
	@ResponseBody
	public void setAppLog(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		userService.saveAppBugLog(parameterMap);
	}
}
