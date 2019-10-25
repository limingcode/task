package com.teach.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.image.tag.utils.FileUtil;
import com.teach.FileUploadMessage;
import com.teach.model.TpCourseware;
import com.teach.model.TpFileUpload;
import com.teach.service.FileUploadService;
import com.teach.service.TpCoursewareService;
import com.teach.service.TpFileTransferRecordService;
import com.teach.util.FileUploadUtil;
import com.teach.util.SendFileUtil;
import com.teach.util.WordToHtml;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/tpFileUpload")
public class TpFileUploadController {
	
	@Resource
	private FileUploadService fileUploadService;
	@Resource
	private TpCoursewareService tpCoursewareService;
	@Resource
	private TpFileTransferRecordService recordService;
	
	/**
	 * 上传视频
	 * @param uploadFile
	 * @param lessonId
	 * @param fileType
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/uploadFile")
	public String uploadFile(MultipartFile uploadFile, int lessonId, byte fileType, HttpServletRequest request) throws IllegalStateException, IOException {
		String fileName = uploadFile.getOriginalFilename();
		FileUploadMessage message = fileUploadService.getIsSupportFileType(fileName, fileType);
		if(!message.isSuccess()) {
			return JSONObject.fromObject(message).toString();
		}
		TpFileUpload fileUpload = new TpFileUpload();
		fileUpload.setFileType(fileType);
		fileName = FileUploadUtil.getUploadName(fileType,fileName);
		String path = FileUploadUtil.getUploadPath(fileType,lessonId);
		String uploadPath = FileUploadUtil.getUploadHomePath() + path; 
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(uploadPath, fileName);
		uploadFile.transferTo(file);
		
		int oaId = (Integer) request.getSession().getAttribute("oaId");
		
		fileUpload.setFileUrl(path + fileName);
		fileUpload.setLessonId(lessonId);
		fileUpload.setUploadDate(new Date());
		fileUpload.setOaId(oaId);
		
		//保存上传信息
		fileUpload = fileUploadService.saveUploadInfo(fileUpload);
		
		if(5 == fileType || 6 == fileType) {//说课、实录视频, 
			SendFileUtil.sendFile(lessonId, fileType, file.getPath(), oaId);
		}
		
		message = new FileUploadMessage(true, "文件上传成功!");
		message.setFileType(fileUpload.getFileType());
		message.setUrl(fileUpload.getFileUrl());
		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 上传话术课件
	 * @param uploadFile
	 * @param lessonId
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/uploadAboveWord")
	public Map<String, Object> uploadAboveWord(MultipartFile uploadFile, int lessonId, HttpServletRequest request) throws IllegalStateException, IOException{
		String fileName = uploadFile.getOriginalFilename();
		String path = FileUploadUtil.getUploadHomePath() + "/upload/temp/";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File wordFile = new File(dir, fileName);
		uploadFile.transferTo(wordFile);
		Map<String, Object> map = WordToHtml.wordToHtml(wordFile.getPath());
		if (!(Boolean) map.get("success")) {
			return map;
		}
		String htmlFilePath = (String) map.get("path");
		String html = FileUtil.readFileByLines(htmlFilePath);
		html = html.replaceAll("<body\\s{1}class=\".*?(?!<)\">", "").replace("</body>", "");
		TpCourseware courseware = tpCoursewareService.getCoursewareByLessonId(lessonId);
		FileUploadUtil.deleteDirectory(path);
		
		if (courseware != null) {
//			courseware.setPageAboveWords(html);
			courseware.setTeachCaseContent(html);
			courseware.setTeachCaseName(fileName);
			courseware = tpCoursewareService.saveCourseware(courseware, null);
			map.put("ret", html);
			map.put("name", fileName);
			return map;
		} else {
			map.put("success", false);
			map.put("message", "该课次未上传课件，请先上传课件");
			return map;
		}
	}
	
	/**
	 * 上传base64图片字符串
	 * @param lessonId
	 * @param imgStr
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadBasa64File", method=RequestMethod.POST)
	public String uploadBasa64File(int lessonId, String imgStr, HttpServletRequest request) {
		FileUploadMessage message = new FileUploadMessage();
		String fileName = FileUploadUtil.getUploadName((byte) 1, "");
		String path = FileUploadUtil.getUploadPath((byte) 1, lessonId);
		String uploadPath = FileUploadUtil.getUploadHomePath() + path;
		if(FileUploadUtil.generateImage(imgStr, uploadPath, fileName)) {
			TpFileUpload fileUpload = new TpFileUpload();
			fileUpload.setFileType((byte) 1);
			fileUpload.setFileUrl(path + fileName);
			fileUpload.setLessonId(lessonId);
			int oaId = (Integer) request.getSession().getAttribute("oaId");
			fileUpload.setOaId(oaId);
			fileUpload.setUploadDate(new Date());
			
			//保存上传信息
			fileUpload = fileUploadService.saveUploadInfo(fileUpload);
			
			message.setSuccess(true);
			message.setMessage("文件上传成功！");
			message.setUrl(path + fileName);
			return JSONObject.fromObject(message).toString();
		}
		message.setSuccess(false);
		message.setMessage("文件上传失败！");
		return JSONObject.fromObject(message).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteVideo", method=RequestMethod.POST)
	public FileUploadMessage deleteVideo(int lessonId, byte type) {
		fileUploadService.deleteVideo(lessonId, type);
		return new FileUploadMessage(true, "删除成功！");
	}
	
	/**
	 * 单个发送文件(teach调用接口)
	 * @param serverId
	 * @param serverAddr
	 * @param lessonId
	 * @param type
	 * @param request
	 */
	@RequestMapping(value="/sendFileToServer")
	public void sendFileToServer(int serverId, String serverAddr, int lessonId, byte type) {
		recordService.sendFileToServer(serverId, serverAddr, lessonId, type);
	}
	
}
