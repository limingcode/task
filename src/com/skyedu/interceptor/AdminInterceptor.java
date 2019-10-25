package com.skyedu.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skyedu.dao.impl.TeacherDAO;
import com.skyedu.model.EduTeacher;
import com.util.SendHttpUrl;
import com.util.StringUtil;

/**
 * 后台拦截器
 * @author xj.chen
 * @version 1.0, 2013-10-24
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {
	
	@Resource private TeacherDAO em;

	static class Menu{
		int menuId;//权限id
		String url;
		int type;//权限类型 1：超级管理权限 2：个人权限 （需要的最低权限级别，当位2时表示最低权限为个人权限。超级管理员权限大于个人权限）
		String info;//权限说明
		public Menu(int menuId,String url, int type, String info){
			this.url=url;
			this.type=type;
			this.info=info;
			this.menuId=menuId;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public int getMenuId() {
			return menuId;
		}
		public void setMenuId(int menuId) {
			this.menuId = menuId;
		}
	}
	public static List<Menu> menuList = new ArrayList<Menu>();//菜单栏
	public static final String OBJ_USER="obj_user_";//session对象的key值
	public static final String OBJ_ACC="obj_acc_";//session权限对象的key值
	public static final String OBJ_RECORDSTAFF = "obj_recordstaff";//session存储操作用户的key值
	/** 权限类别-超级管理员 */
	public static final int ACC_TYPE_SYS=1;
	/** 权限类别-个人权限 */
	public static final int ACC_TYPE_USER=2;
	
	public void destroy() {
	}

	static {
		//装载权限初始化数据
		menuList.add(new Menu(1110,"/admin/base/user.jhtml",ACC_TYPE_SYS,"员工管理-初始化页面"));
		menuList.add(new Menu(1110,"/admin/base/list.jhtml",ACC_TYPE_SYS,"员工管理-数据请求"));

		menuList.add(new Menu(1111,"/admin/base/squad.jhtml",ACC_TYPE_SYS,"小组管理-初始化页面"));
		menuList.add(new Menu(1111,"/admin/base/updSquad.jhtml",ACC_TYPE_SYS,"小组管理-曾改数据"));
		menuList.add(new Menu(1111,"/admin/base/delSquad.jhtml",ACC_TYPE_SYS,"小组管理-删除数据"));
		
		menuList.add(new Menu(1112,"/admin/base/skill.jhtml",ACC_TYPE_SYS,"技能管理-初始化页面"));
		menuList.add(new Menu(1112,"/admin/base/updSkill.jhtml",ACC_TYPE_SYS,"技能管理-曾改数据"));
		menuList.add(new Menu(1112,"/admin/base/delSkill.jhtml",ACC_TYPE_SYS,"技能管理-删除数据"));

		menuList.add(new Menu(1113,"/admin/base/certificate.jhtml",ACC_TYPE_SYS,"证书管理-初始化页面"));
		menuList.add(new Menu(1113,"/admin/base/updCertificate.jhtml",ACC_TYPE_SYS,"证书管理-曾改数据"));
		menuList.add(new Menu(1113,"/admin/base/delCertificate.jhtml",ACC_TYPE_SYS,"证书管理-删除数据"));
		
		menuList.add(new Menu(1114,"/admin/base/scoreFlow.jhtml",ACC_TYPE_USER,"积分流水-初始化页面"));
		menuList.add(new Menu(1114,"/admin/base/scoreFlowList.jhtml",ACC_TYPE_USER,"积分流水-数据请求"));
		menuList.add(new Menu(1117,"/admin/base/setFlow.jhtml",ACC_TYPE_SYS,"积分流水-作废流水"));//作废时同时清理得分
		
		menuList.add(new Menu(1115,"/admin/base/allotFlow.jhtml",ACC_TYPE_USER,"配分流水-初始化页面"));
		menuList.add(new Menu(1115,"/admin/base/allotFlowList.jhtml",ACC_TYPE_USER,"积分流水-数据请求"));
		
		menuList.add(new Menu(1116,"/admin/base/rankDay.jhtml",ACC_TYPE_USER,"今日排名-初始化页面"));
		menuList.add(new Menu(1116,"/admin/base/rankDayList.jhtml",ACC_TYPE_USER,"今日排名-数据请求"));
		
		Logger.getLogger(AdminInterceptor.class).warn("系统菜单---数据装载成功!");
	}

	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
 		String u = request.getParameter("u");
 		String p = request.getParameter("p");
 		String id = request.getParameter("UserID");
 		if(StringUtil.isEmpty(id))
 			id=request.getParameter("id");
 		EduTeacher record = (EduTeacher) session.getAttribute(OBJ_RECORDSTAFF);
 		//操作者数据更新
		/*if(!StringUtil.isEmpty(id)&&(record==null||!id.equals(record.getId()+""))){
			EduTeacher obj = em.findByOaId(Integer.valueOf(id));
			if(null != obj){
				session.setAttribute(OBJ_RECORDSTAFF, obj);
			}
		}*/
		//获取vo end
 		if(!StringUtil.isEmpty(u)&&!StringUtil.isEmpty(p)){
 			session.setAttribute(OBJ_USER+"userName", request.getParameter("u"));
			session.setAttribute(OBJ_USER+"pwd", request.getParameter("p"));
 		}
 		String userName = (String) session.getAttribute(OBJ_USER+"userName");
 		String password = (String) session.getAttribute(OBJ_USER+"pwd");
		String path=request.getRequestURI();
		List<Menu> accList = new ArrayList<Menu>();
		boolean bol = true;
		//装置需要判断的权限序列
		for(Menu acc : menuList){
			if(!StringUtil.isEmpty(acc.getUrl())&&path != null&& path.equals(acc.getUrl()))
				accList.add(acc);
		}
		//装置需要判断的权限序列 end
		if(accList.size()>0){//需要权限判断 
			for(Menu acc : accList){
				//session处理
				Map<String, Object> obj = (Map<String, Object>) session.getAttribute(OBJ_ACC+acc.getMenuId());
				if(null == obj||
						(!StringUtil.isEmpty(userName)&&!StringUtil.isEmpty(password)&&(
								!userName.equals(obj.get("userName"))||!password.equals(obj.get("password"))))
						){//需要登录
					obj = accLogin(acc.getMenuId(), session.getAttribute(OBJ_USER+"userId")+"", userName, password);
					if(null != obj && Integer.valueOf(obj.get("id")+"")!=0){//登录成功
						obj.put("userName", userName);
						obj.put("password", password);
						session.setAttribute(OBJ_ACC+acc.getMenuId(), obj);
						session.setAttribute(OBJ_USER+"userId", obj.get("id"));//存放用户id到session中
					}else{
						session.removeAttribute(OBJ_ACC+acc.getMenuId());
						session.removeAttribute(OBJ_USER+"userId");//存放用户id到session中
					}
				}
				//session处理 end
				//重新获取session中的权限数据
				obj = (Map<String, Object>) session.getAttribute(OBJ_ACC+acc.getMenuId());
				//权限处理
				if(obj==null) bol = false;
				else{
					bol = (Boolean) obj.get("flag");
					if(!bol){
						//如果没有权限则判断个人权限
						int teacherId = Integer.valueOf(obj.get("teacherId")+"");
						if(teacherId==0){
							bol = false;//无权限
						}else if(acc.getType()==ACC_TYPE_USER)
							bol = true;
					}
				}
				if(!bol) break;//发现无权限情况则跳出循环
			}
		}
		if(bol){//有权限处理
			//默认值处理
			return super.preHandle(request, response, handler);
		}else{//没有权限
			String requestType = request.getParameter("requestType");
			if(isAjaxRequest(request)||(!StringUtil.isEmpty(requestType)&&requestType.trim().equals("android"))){  //判断是否为json请求
				response.setContentType("application/json; charset=utf-8");
				PrintWriter writer = response.getWriter();
        		writer.write("{\"PERMISSION\":\"NO\"}");
        		return false;
        	}else{
        		response.sendRedirect("/NOPERMISSION.html");
        		return false;
        	}
		}
	}
	/**
	 * 判断是否为ajax请求
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
	}
	
	public Map<String, Object> accLogin(int menuId, String userId, String userName, String pwd){
		return SendHttpUrl.sendPwd(menuId,userId, userName, pwd);
	}
}
