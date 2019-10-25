package com.teach.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.teach.FileUploadMessage;
import com.teach.Page;
import com.teach.model.TpPersonFile;
import com.teach.service.PersonFileService;
import com.teach.util.PersonFileUtil;

@Controller
@RequestMapping("/personFile")
public class PersonFileController {
	
	@Resource
	private PersonFileService personFileService;
	
	/**
	 * go个人文件夹页面
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toPersonFile", method=RequestMethod.GET)
	public String toPersonFile(int userId, Model model) {
		long dirTotalSize = PersonFileUtil.getDirTotalSize(PersonFileUtil.getHomePath() + PersonFileUtil.getPersonDirPath(userId));
		model.addAttribute("dirSize", dirTotalSize);
		model.addAttribute("dirTotalSize", String.format("%.2f", (float)dirTotalSize/(1024*1024)));
		return "manage/personFiles";
	}
	
	/**
	 * 得到个人文件分页列表
	 * @param oaId
	 * @param search
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getPersonFileList", method=RequestMethod.POST)
	public String getPersonFileList(int oaId, String search, Page page) {
		page = personFileService.getPersonFileList(oaId, search, page);
		return JSONObject.toJSONString(page);
	}
	
	/**
	 * 文件上传
	 * @param uploadFile
	 * @param oaId
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/uploadFile")
	public String uploadFile(MultipartFile uploadFile, int oaId) throws IllegalStateException, IOException {
		String fileName = uploadFile.getOriginalFilename();
		//格式限制
		FileUploadMessage message = personFileService.getIsSupportFileType(fileName);
		if(!message.isSuccess()) {
			return JSONObject.toJSONString(message);
		}
		if (PersonFileUtil.getFileIsExist(oaId, fileName)) {
			return JSONObject.toJSONString(new FileUploadMessage(false, "存在同名文件!"));
		}
		String homePath = PersonFileUtil.getHomePath();
		String personDirPath = PersonFileUtil.getPersonDirPath(oaId);
		File dir = new File(homePath + personDirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (PersonFileUtil.getDirTotalSize(homePath + personDirPath) + uploadFile.getSize() > 500 * 1024 * 1024) {
			return JSONObject.toJSONString(new FileUploadMessage(false, "空间不足！"));
		}
		File file = new File(homePath + personDirPath, fileName);
		uploadFile.transferTo(file);
		
		TpPersonFile personFile = new TpPersonFile();
		personFile.setFileName(fileName);
		personFile.setFilePath(personDirPath + fileName);
		personFile.setOaId(oaId);
		personFile.setFileSize(uploadFile.getSize());
		personFile.setUploadTime(new Date());
		
		personFileService.savePersonFile(oaId, personFile);
		
		message = new FileUploadMessage(true, "文件上传成功!");
		return JSONObject.toJSONString(message);
	}
	
	/**
	 * 文件重命名
	 * @param id
	 * @param fileName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="reNameFile", method=RequestMethod.POST)
	public String reNameFile(int id, String fileName) {
		FileUploadMessage message = personFileService.reNameFile(id, fileName);
		return JSONObject.toJSONString(message);
	}
	
	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="deleteFile", method=RequestMethod.POST)
	public String deleteFile(int id) {
		personFileService.deleteFile(id);
		return JSONObject.toJSONString(new FileUploadMessage(true, "删除成功！"));
	}
	
	/**
	 * go 更改学生头像页面
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value="toChangeStudentHead", method=RequestMethod.GET)
	public String toChangeStudentHead(int oaId, Model model){
		List<Map<String, Object>> allCourse = personFileService.getAllCourse(oaId);
		if (allCourse != null && allCourse.size() > 0) {
			List<Map<String, Object>> students = personFileService.getStudentList(Integer.valueOf(String.valueOf(allCourse.get(0).get("id"))));
			model.addAttribute("students", students);
		}
		model.addAttribute("allCourse", allCourse);
		return "manage/studentAvatar";
	}
	
	/**
	 * 得到所有学生
	 * @param courseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getStudents", method=RequestMethod.POST)
	public List<Map<String, Object>> getStudents(int courseId){
		return personFileService.getStudentList(courseId);
	}
	
	/**
	 * 单个发送文件(teach自动调用接口)
	 * @param id
	 */
	@RequestMapping(value="/sendFileToServer")
	public void sendFileToServer(int id) {
		personFileService.sendFileToServer(id);
	}
	
	/**
	 * 单个发送文件(teach手动调用接口)
	 * @param id
	 */
	@RequestMapping(value="/sendFile")
	public void sendFile(int id, String serverIp) {
		personFileService.sendFile(id, serverIp);
	}
}
