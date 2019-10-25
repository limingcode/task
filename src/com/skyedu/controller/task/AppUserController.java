package com.skyedu.controller.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skyedu.dao.impl.UserDAO;
import com.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.skyedu.model.AppContent;
import com.skyedu.model.AppVersion;
import com.skyedu.model.StudentLoginLog;
import com.skyedu.service.AppZipService;
import com.skyedu.service.CourseService;
import com.skyedu.service.MessageService;
import com.skyedu.service.RankService;
import com.skyedu.service.UserService;
import com.util.FileUtil;

@Controller
@RequestMapping("/appUser")
public class AppUserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private RankService rankService;
	@Autowired
	private AppZipService appZipService;
	@Autowired
	private MessageService messageService;
    @Autowired
    private UserDAO userDAO;

	@RequestMapping("/courseList")
	@ResponseBody
	public Map<String,Object> getCourseList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> result = new HashMap<String,Object>();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			result.put("code", 101);
			result.put("message", "非法");
		}else{
			Map<String, Object> student = userService
					.getStudent(username.trim(), password);
			if (student != null) {
				List<Map<String, Object>> courseList = courseService
						.getHierarchyListNew((Integer) student.get("id"));
				int count = messageService.getUnreadCount((Integer)student.get("id"));
				student.put("messageCount", count);
				student.put("courseList", courseList);
				student.put("iD", student.get("id"));
				Object object = student.get("sex");
				if(object==null || (object instanceof String && StringUtils.isEmpty((String)object))){
					student.put("sex", "2");
				}else if("男".equals((String)object)){
					student.put("sex", "0");
				}else if("女".equals((String)object)){
					student.put("sex", "1");
				}else{
					student.put("sex", "2");
				}
				String header = request.getHeader("user-agent");
				StudentLoginLog studentLoginLog = new StudentLoginLog((Integer)student.get("id"), new Date(), header);
				userService.saveStudentLoginLog(studentLoginLog);
				result.put("code", 100);
				result.put("message", "成功");
				result.put("data", student);
			}else{
           		result.put("code", 101);
				result.put("message", "失败");

			}
		}
  		return result;
	}

	@RequestMapping("/studentInfo")
	@ResponseBody
	public Map<String,Object> getSutdentInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> result = new HashMap<String,Object>();
		String userStudentId = request.getParameter("userStudentId");
		if (StringUtils.isEmpty(userStudentId)) {
			result.put("code", 101);
			result.put("message", "非法");
		}else{
			Map<String, Object> student = userService.getStudent(Integer.parseInt(userStudentId),1);
			if (student != null) {
				student.put("iD", student.get("id"));
				Object object = student.get("sex");
				if(object==null || StringUtils.isEmpty((String)object)){
					student.put("sex", 2);
				}else if("男".equals((String)object)){
					student.put("sex", 0);
				}else if("女".equals((String)object)){
					student.put("sex", 1);
				}else{
					student.put("sex", 2);
				}
				student.put("designationList", rankService.getDesignationList(Integer.parseInt(userStudentId)));
				result.put("code", 100);
				result.put("message", "成功");
				result.put("data", student);
			}else{
				result.put("code", 101);
				result.put("message", "失败");
			}
		}
		return result;
	}

	@Deprecated
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> result = new HashMap<String,Object>();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			result.put("code", 101);
			result.put("message", "非法");
		}else{
			Map<String, Object> student = userService
					.getStudent(username.trim(), password);
			if (student != null) {
				List<Map<String, Object>> courseList = courseService
						.getHierarchyList((Integer) student.get("id"));
				int count = messageService.getUnreadCount((Integer)student.get("id"));
				student.put("messageCount", count);
				student.put("courseList", courseList);
				student.put("iD", student.get("id"));
				Object object = student.get("sex");
				if(object==null || StringUtils.isEmpty((String)object)){
					student.put("sex", 2);
				}else if("男".equals((String)object)){
					student.put("sex", 0);
				}else if("女".equals((String)object)){
					student.put("sex", 1);
				}else{
					student.put("sex", 2);
				}
				String header = request.getHeader("user-agent");
				StudentLoginLog studentLoginLog = new StudentLoginLog((Integer)student.get("id"), new Date(), header);
				userService.saveStudentLoginLog(studentLoginLog);
				result.put("code", 100);
				result.put("message", "成功");
				result.put("data", student);
			}else{
				result.put("code", 101);
				result.put("message", "失败");
			}
		}
  		return result;
	}
	
	@RequestMapping("/loginTest")
	@ResponseBody
	public Map<String,Object> loginTest(@RequestParam String username,@RequestParam String password) {
		Map<String,Object> result = new HashMap<String,Object>();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			result.put("code", 101);
			result.put("message", "非法");
		}else{
			Map<String, Object> student = userService
					.getStudent(username.trim(), password);
			if (student != null) {
				result.put("code", 100);
				result.put("message", "成功");
			}else{
				result.put("code", 101);
				result.put("message", "失败");
			}
		}
  		return result;
	}
	
	@RequestMapping("/getCourseInfoList")
	@ResponseBody
	public Map<String,Object> getCourseInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> result = new HashMap<String,Object>();
		String userStudentId = request.getParameter("userStudentId");
		if (StringUtils.isEmpty(userStudentId)) {
			result.put("code", 101);
			result.put("message", "非法");
		}else{
			List<Map<String, Object>> courseInfoList = courseService.getCourseInfoList(Integer.parseInt(userStudentId));
			for (Iterator<Map<String, Object>> iterator = courseInfoList.iterator(); iterator
					.hasNext();) {
				Map<String, Object> courseInfo = (Map<String, Object>) iterator.next();
//				Object object2 = courseInfo.get("photo");
//				if (object2!=null) {
//					String photo = "http://www.actionsky.net/"+(String) object2;
//					courseInfo.put("photo", photo);
//				}
			}
			result.put("code", 100);
			result.put("message", "成功");
			result.put("data", courseInfoList);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/saveImgByTask")
	public Map<String, Object> saveImgByTask(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<>();
		int userStudentId = Integer.valueOf(request.getParameter("userStudentId"));
		String imgStr = request.getParameter("imgStr");
		String path = request.getSession().getServletContext().getRealPath("/");
		String fileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
		String makePath = "/upload/production/" + userStudentId % 15 + "/" + userStudentId % 16 + "/" + userStudentId +"/";
		if(FileUtil.generateImage(imgStr, path+makePath, fileName)) {
			Map<String, Object> student = userService.getStudent(userStudentId,1);
			String oldImg = (String) student.get("img");
			userService.changImg(userStudentId, makePath + "/"+ fileName, 1);
			/*****删除旧头像*****/
			try {
				File oldFile = new File(path + oldImg);
				if (oldFile.exists()) {
					oldFile.delete();
				}
			} catch (Exception e) {
			}
			/*****删除旧头像*****/
			map.put("code", 100);
			map.put("message", "头像更新成功");
			map.put("imageSrc",  makePath + "/" + fileName);
		}
		return map;
	}

	@RequestMapping("/studentImg")
	@ResponseBody
	public Map<String,Object> saveImg(
			@RequestParam(value = "file") MultipartFile[] multipartFile,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		String studentId = request.getParameter("userStudentId");
		String path = request.getSession().getServletContext().getRealPath("/");
		String makePath = "";
		String fileName = "";
		if (studentId != null) {
			/** 上传文件处理内容 **/
			try {
				for (int i = 0; i < multipartFile.length; i++) {
					MultipartFile file = multipartFile[i];
					String oriName = file.getOriginalFilename();
					if (StringUtils.isEmpty(oriName)) {
						continue;
					}
					fileName = UUID.randomUUID().toString().replace("-", "")
							+ oriName.substring(oriName.lastIndexOf("."));
					makePath = "/upload/production/"
							+ Integer.valueOf(studentId) % 15 + "/" + Integer.valueOf(studentId) % 16
							+ "/" + Integer.valueOf(studentId);
					File dir = new File(path + makePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File targetFile = new File(dir, fileName);
					file.transferTo(targetFile);
				}




                Map<String, Object> student = userService
                        .getStudent(Integer.valueOf(studentId),1);
                if (!StringUtils.isEmpty(student)) {
                    String oldImg = (String) student.get("img");
                    /*****删除旧头像*****/
                    try {
                        File oldFile = new File(path + oldImg);
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                userService.changImg(Integer.valueOf(studentId), makePath + "/"
                        + fileName, 1);
				/*****删除旧头像*****/
                String imgUrl=makePath+fileName;
                final HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("img",imgUrl);
                result.put("code", 100);
                result.put("data",objectObjectHashMap);
				result.put("message", "头像更新成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("code", 101);
				result.put("message", "头像更新失败");
			}
		}else{
			result.put("code", 101);
			result.put("message", "参数错误");
		}
		return result;
	}

    @RequestMapping("/studentImgHz")
    @ResponseBody
    public Map<String,Object> saveImgHz(
            @RequestParam(value = "file") MultipartFile[] multipartFile,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        String studentId = request.getParameter("userStudentId");
        String path = request.getSession().getServletContext().getRealPath("/");
        String makePath = "";
        String fileName = "";
        if (studentId != null) {
            /** 上传文件处理内容 **/
            try {
                for (int i = 0; i < multipartFile.length; i++) {
                    MultipartFile file = multipartFile[i];
                    String oriName = file.getOriginalFilename();
                    if (StringUtils.isEmpty(oriName)) {
                        continue;
                    }
                    fileName = UUID.randomUUID().toString().replace("-", "")
                            + oriName.substring(oriName.lastIndexOf("."));
                    makePath = "/upload/production/hz/"
                            + Integer.valueOf(studentId) % 15 + "/" + Integer.valueOf(studentId) % 16
                            + "/" + Integer.valueOf(studentId);
                    File dir = new File(path + makePath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File targetFile = new File(dir, fileName);
                    file.transferTo(targetFile);
                }

                Map<String, Object> student = userService
                        .getStudent(Integer.valueOf(studentId),2);
                if (!StringUtils.isEmpty(student)) {
                    String oldImg = (String) student.get("img");
                    /*****删除旧头像*****/
                    try {
                        File oldFile = new File(path + oldImg);
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                userService.changImg(Integer.valueOf(studentId), makePath + "/"
                        + fileName, 2);
                /*****删除旧头像*****/
                result.put("code", 100);
                result.put("message", "头像更新成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.put("code", 101);
                result.put("message", "头像更新失败");
            }
        }else{
            result.put("code", 101);
            result.put("message", "参数错误");
        }
        return result;
    }

	@RequestMapping("/changeTip")
	@ResponseBody
	public Map<String,Object> changeTip(HttpServletRequest request,
			HttpServletResponse response) {
		String studentId = request.getParameter("userStudentId");
		String tip = request.getParameter("tip");
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			userService.changTip(Integer.valueOf(studentId), Integer.valueOf(tip));
			data.put("code", 100);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data.put("code", 101);
			data.put("message", "修改异常");
		}
		return data;
	}
	
	@RequestMapping("/getAppVersion")
	@ResponseBody
	public Map<String,Object> getAppVersion(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> data = new HashMap<String, Object>();
		Map<String,Object> appVersion = appZipService.getAppVersion();
		data.put("code", 100);
		data.put("appVersion", appVersion);
		return data;
	}
	
	@RequestMapping("/setAppVersion")
	@ResponseBody
	public Map<String,Object> setAppVersion(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> data = new HashMap<String, Object>();
		String version = request.getParameter("version");
		String path = request.getParameter("path");
		String[] contents = request.getParameterValues("content");
		if (StringUtils.isEmpty(version)) {
			data.put("code", 101);
			data.put("message", "参数错误");
		}else{
			AppVersion appVersion = new AppVersion();
			appVersion.setCreateDate(new Date());
			appVersion.setVersion(version);
			appVersion.setPath(path);
			List<AppContent> appContentList = new ArrayList<AppContent>();
			if (contents!=null && contents.length>0) {
				for (int i = 0; i < contents.length; i++) {
					String content = contents[i];
					AppContent appContent = new AppContent();
					appContent.setAppVersion(appVersion);
					appContent.setContent(content);
					appContent.setCreateDate(new Date());
					appContentList.add(appContent);
				}
			}
			appVersion.setAppContentList(appContentList);
			appZipService.setAppVersion(appVersion);
			data.put("code", 100);
			data.put("message", "保存成功");
		}
		return data;
	}
}
