package com.skyedu.controller.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.skyedu.model.Attachment;
import com.skyedu.model.Production;
import com.skyedu.service.ProductionService;

@Controller
@RequestMapping("/appProduction")
public class AppProductionController {

	@Autowired
	private ProductionService productionService;

	@RequestMapping("/commitProduction")
	@ResponseBody
	public Map<String, Object> commitProduction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String studentId = request.getParameter("userStudentId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		String opusList = request.getParameter("opusList");

		String path = request.getSession().getServletContext().getRealPath("/");
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(new Date());*/
		// 按日期存储不利于日后删除工作
		/*
		 * String shortPath = File.separatorChar + "upload" + File.separatorChar
		 * + "product" + File.separatorChar + format.substring(0, 4) +
		 * File.separatorChar + format.substring(4) + File.separatorChar;
		 */
		// 按学生id存储
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = sdf.format(new Date());
		String shortPath = "/upload/product/"+time.substring(0, 4)+"/"+time.substring(4, 6)+"/"+time.substring(6);
		if (studentId != null) {
			/** 上传文件处理内容 **/
			try {

				Production production = new Production();
				production.setContent(content);
				production.setCreateDate(new Date());
				production.setStudent(Integer.parseInt(studentId));
				production.setTitle(title);
				production.setType(Integer.valueOf(type));
				List<Attachment> arrachments = new ArrayList<Attachment>();
				if (!StringUtils.isEmpty(opusList)) {
					
					List<Map<String, Object>> parse = (List<Map<String, Object>>) JSONObject
							.parse(opusList);
					for (Iterator<Map<String, Object>> iterator = parse.iterator(); iterator
							.hasNext();) {
						Map<String, Object> map = (Map<String, Object>) iterator
								.next();
						Attachment attachment = new Attachment();
						attachment.setAttachmentName((String) map.get("name"));
						attachment.setProduction(production);
						attachment.setType((Integer) map.get("type"));
						
						/*********** 文件转移 **************/
						String fileName = "";
						try {
							File file = new File(path + shortPath);
							if (!file.exists()) {
								file.mkdirs();
							}
							String url = (String) map.get("url");
							fileName = url.substring(url.lastIndexOf("\\"));
							File tempImg = new File(path + url);
							File img = new File(path + shortPath, fileName);
							FileUtils.copyFile(tempImg, img);
							// 删除临时文件
							tempImg.delete();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						/*********** 文件转移 **************/
						
						attachment.setUrl(shortPath + fileName);
						arrachments.add(attachment);
					}
				}
				production.setAttachments(arrachments);
				productionService.saveProduction(production);
				result.put("code", 100);
				result.put("message", "作品上传成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("code", 101);
				result.put("message", "作品上传失败");
			}
		} else {
			result.put("code", 101);
			result.put("message", "参数错误");
		}
		return result;
	}

	@RequestMapping("/getProductionList")
	@ResponseBody
	public Map<String, Object> getProductionList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String studentId = request.getParameter("targetId");
		String pageNo = request.getParameter("pageNo");
		if (!StringUtils.isEmpty(studentId)) {
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = "1";
			}
			List<Map<String,Object>> productionList = productionService
					.getProductionList(Integer.valueOf(studentId),Integer.valueOf(pageNo));
			result.put("code", 100);
			result.put("data", productionList);
		} else {
			result.put("code", 101);
			result.put("message", "参数错误");
		}
		return result;
	}
	
	@RequestMapping("/removeProduction")
	@ResponseBody
	public Map<String, Object> removeProduction(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String studentId = request.getParameter("userStudentId");
		String productionId = request.getParameter("productionId");
		if (!StringUtils.isEmpty(studentId) && !StringUtils.isEmpty(productionId)) {
			String path = request.getSession().getServletContext().getRealPath("/");
			List<Map<String, Object>> attachmentList = productionService.getAttachmentList(Integer.valueOf(productionId));
			for (Iterator<Map<String, Object>> iterator = attachmentList.iterator(); iterator.hasNext();) {
				Map<String, Object> attachment = (Map<String, Object>) iterator.next();
				String url = (String) attachment.get("url");
				File file = new File(path,url);
				try {
					file.delete();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			productionService.delProduction(Integer.valueOf(studentId), Integer.valueOf(productionId));
			result.put("code", 100);
			result.put("message", "删除成功");
		} else {
			result.put("code", 101);
			result.put("message", "参数错误");
		}
		return result;
	}
	
	@RequestMapping("/uploadFile")
	@ResponseBody
	public Map<String,Object> uploadFile(
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
					makePath = File.separatorChar + "upload" + File.separatorChar
							+ "temp";
					File dir = new File(path + makePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File targetFile = new File(dir, fileName);
					file.transferTo(targetFile);
				}
				result.put("url", makePath+File.separatorChar+fileName);
				System.out.println(makePath+File.separatorChar+fileName);
				result.put("code", 100);
				result.put("message", "上传成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("code", 101);
				result.put("message", "上传失败");
			}
		}else{
			result.put("code", 101);
			result.put("message", "参数错误");
		}
		return result;
	}

	/*
	 * public Map<String,Object> readProduction(HttpServletRequest request,
	 * HttpServletResponse response){ Map<String, Object> result = new
	 * HashMap<String, Object>(); String productionId =
	 * request.getParameter("productionId"); }
	 */
}
