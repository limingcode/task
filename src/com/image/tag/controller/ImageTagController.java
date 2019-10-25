package com.image.tag.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skyedu.model.book.SendBookJsonRecordModel;
import org.apache.axiom.util.base64.Base64Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.image.tag.Message;
import com.image.tag.model.ImBook;
import com.image.tag.service.ImageTagService;
import com.image.tag.utils.FileUtil;
import com.image.tag.utils.ImageTagSendFileUtil;
import com.image.tag.utils.ImageTagUtils;
import com.skyedu.service.BaseService;
import com.skyedu.service.LessonService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 * 图片标注Controller
 */
@Controller
@RequestMapping("/imageTag")
public class ImageTagController {
	
	@Resource
	private ImageTagService imageTagService;
	@Resource
	private BaseService baseService;
	@Resource
	private LessonService lessonService;
	
	@RequestMapping(value="/chooseBook", method=RequestMethod.GET)
	public String chooseBook(Model modelMap) {
		List<Map<String, Object>> gradeList = baseService.gradeList();
		List<Map<String, Object>> subjectList = baseService.subjectList();
//		List<Map<String, Object>> cateList = baseService.cateList();
//		List<Map<String, Object>> periodList = baseService.periodList();
//		List<Map<String, Object>> bookList = imageTagService.getBookTypeList();
		modelMap.addAttribute("gradeList", gradeList);
		modelMap.addAttribute("subjectList", subjectList);
//		modelMap.addAttribute("cateList", cateList);
//		modelMap.addAttribute("periodList", periodList);
//		modelMap.addAttribute("bookList", bookList);
		return "imageTag/chooseBook";
	}
	
	/**
	 * 通过科目、年级、层次、学期获取课次
	 * @param subject 科目
	 * @param grade 年级
	 * @param cate 层次
	 * @param period 学期
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getLesson", method=RequestMethod.POST)
	public String getLesson(String subject, String grade, String cate, String period) {
		List<Map<String, Object>> lessonList = lessonService.getLessonList(period, grade, subject, cate);
		boolean b = lessonList != null && lessonList.size() >0;
		Message message = new Message(b, b ? null : "当前层次无课次！");
		JSONObject json = JSONObject.fromObject(message);
		return json.toString();
	}
	
	@RequestMapping(value="/uploadImageFile")
	public String uploadImageFile(@RequestParam("uploadFiles")MultipartFile[] uploadFiles, int bookId, String period, String bookName) throws IllegalStateException, IOException {
		String path = ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, false);
		String realPath = ImageTagUtils.getHomePath() + path;
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		for (int i = 0; i < uploadFiles.length; i++) {
			MultipartFile uploadFile = uploadFiles[i];
			String fileName = uploadFile.getOriginalFilename();
			fileName = ImageTagUtils.getImageUploadName(fileName, (i+1));
			File file = new File(realPath, fileName);
			uploadFile.transferTo(file);
		}
		return "redirect:/imageTag/editImageTag.jhtml?bookId="+bookId+"&period="+period+"&bookName="+bookName;
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadImageFileAjax")
	public String uploadImageFileAjax(@RequestParam("uploadFiles")MultipartFile[] uploadFiles, int bookId, String period, String bookName) throws IllegalStateException, IOException {
		String path = ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, false);
		String realPath = ImageTagUtils.getHomePath() + path;
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uploadFiles.length; i++) {
			MultipartFile uploadFile = uploadFiles[i];
			String fileName = uploadFile.getOriginalFilename();
			fileName = ImageTagUtils.getImageUploadName(fileName, (i+1));
			File file = new File(realPath, fileName);
			uploadFile.transferTo(file);
			list.add(fileName);
		}
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/updateZipSize")
	public String updateZipSize() {
		imageTagService.updateZipSize();
		return "successful";
	}
	
	@RequestMapping(value="/editImageTag", method=RequestMethod.GET)
	public String editImageTag(int bookId, String period, Model model) throws UnsupportedEncodingException {
		ImBook book = imageTagService.getBook(bookId);
		String bookName = book.getBookName();
		List<Map<String, Object>> lessonList = imageTagService.getLessonList(String.valueOf(bookId));
		String lessonStr = "<select>";
		for (Map<String, Object> map : lessonList) {
			lessonStr += "<option lessonId=\""+ String.valueOf(map.get("id")) +"\" lessonTitle=\""+ String.valueOf(map.get("name")) +"\" value=\""+ String.valueOf(map.get("orderNo")) +"\" lessonDesc=\""+ map.get("description") +"\" lessonPop=\""+ map.get("lessonPop") +"\">"+ String.valueOf(map.get("orderNo"))+ "." +String.valueOf(map.get("name")) +"</option>";
		}
		lessonStr += "</select>";
		boolean updataImageTag = imageTagService.isUpdataImageTag(String.valueOf(bookId), period, bookName);
		String filePath = ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, updataImageTag);
		String tempFilePath = ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, false);
		String jsonFile = ImageTagUtils.getHomePath() + ImageTagUtils.getJsonFile(bookId, period, bookName);
		String json = FileUtil.readFileByLines(jsonFile);
		List<String> list = imageTagService.getImageList(json, bookId, period, bookName, updataImageTag);
//		if (list == null || list.size() == 0) {
//			return "redirect:/imageTag/toUploadFile.jhtml?bookId="+bookId+"&period="+period+"&bookName="+bookName;
//		}
        json= StringUtils.isEmpty(json) ? json :new BASE64Encoder().encode(json.replace("'", "`").getBytes(StandardCharsets.UTF_8)).replaceAll("\r","").replace("\n","");

        model.addAttribute("json", json);
		model.addAttribute("list", list);
		model.addAttribute("lesson", lessonList);
		model.addAttribute("lessonStr", lessonStr.replace("'", "`"));
		model.addAttribute("filePath", filePath);
		model.addAttribute("tempFilePath", tempFilePath);
		model.addAttribute("bookName", bookName);
		return "imageTag/editImageTag";
	}


	@ResponseBody
	@RequestMapping(value="/uploadAudioFile", method=RequestMethod.POST)
	public String uploadAudioFile(MultipartFile uploadFile, String period, int bookId, String bookName) throws IllegalStateException, IOException {
		String fileName = uploadFile.getOriginalFilename();
		Message message = ImageTagUtils.getIsSupportFileType(fileName, (byte) 2);
		if(!message.isSuccess()) {
			return JSONObject.fromObject(message).toString();
		}
		fileName = ImageTagUtils.getAudioUploadName(fileName,bookId);
		String path = ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 2, false);
		String realPath = ImageTagUtils.getHomePath() + path;
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(realPath, fileName);
		uploadFile.transferTo(file);
		message = new Message(true, "文件上传成功！");
		message.setUrl(fileName);
		return JSONObject.fromObject(message).toString();
	}

    /**
     * 保存sees
     * @param bookId
     * @param period
     * @param bookName
     * @param jsonString
     * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping(value="/saveImageTag", method=RequestMethod.POST)
	public String saveImageTag(int bookId, String period, String bookName, String jsonString) throws Exception {
		imageTagService.saveImageTag(bookId, period, bookName, jsonString);
		Message message = new Message(true, "提交成功！");
		return JSONObject.fromObject(message).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/getCateAndStatus", method=RequestMethod.POST)
	public String getCateAndStatus(String subject, String grade, String period, String level) {
		List<Map<String, Object>> status = imageTagService.getCateAndStatus(subject, grade, period, level);
		Message message = new Message();
		message.setList(status);
		return JSONObject.fromObject(message).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteTempFile", method=RequestMethod.POST)
	public String deleteTempFile(int bookId, String period, String bookName) {
		String tempPath = ImageTagUtils.getHomePath() + ImageTagUtils.getBookPath(bookId, period, bookName, false);
		FileUtil.deleteDir(tempPath);
		Message message = new Message();
		return JSONObject.fromObject(message).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadLesson", method=RequestMethod.POST)
	public String uploadLesson(int lessonId, @RequestParam("zipFile")MultipartFile zipFile) throws IllegalStateException, IOException {
		Map<String, Object> lesson = imageTagService.getLesson(lessonId);
		int bookId = Integer.parseInt(String.valueOf(lesson.get("fId")));
		ImBook book = imageTagService.getBook(bookId);
		String bookName = book.getBookName();
		String uploadPath = ImageTagUtils.getHomePath() + 
				ImageTagUtils.getBookPath(bookId, "76", bookName, false) + lessonId + File.separator;
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(uploadPath + lessonId + ".zip");
		zipFile.transferTo(file);
		FileUtil.unZip(uploadPath, file);
		
		Message message = imageTagService.saveLesson(bookId, bookName, lessonId, String.valueOf(lesson.get("name")));
		return JSONObject.fromObject(message).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadBook", method=RequestMethod.POST)
	public String uploadBook(int bookId, @RequestParam("zipFile")MultipartFile zipFile) throws IllegalStateException, IOException {
		ImBook book = imageTagService.getBook(bookId);
		String bookName = book.getBookName();
		String uploadPath = ImageTagUtils.getHomePath() + 
				ImageTagUtils.getBookPath(bookId, "76", bookName, false);
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(uploadPath + bookId + ".zip");
		zipFile.transferTo(file);
		FileUtil.unZip(uploadPath, file);
		
		Message message = imageTagService.saveBook(bookId, bookName);
		return JSONObject.fromObject(message).toString();
	}




	/**
	 * 文件接收
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/receiveFile")
	public void receiveFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String bookId = request.getHeader("bookId");
		String period = request.getHeader("period");
		ImBook book = imageTagService.getBook(Integer.valueOf(bookId));
		String bookName = book.getBookName();
//		String path = ImageTagUtils.getZipFile(bookId, period, URLEncoder.encode(bookName, "UTF-8"));
		String path = ImageTagUtils.getZipFile(bookId, period, bookName.replace("朗文", "langwen").replaceAll("\\s", "_"));
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		ServletInputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			inputStream = request.getInputStream();
			fileOutputStream = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n=inputStream.read(b)) != -1) {
				fileOutputStream.write(b, 0, n);
			}
			fileOutputStream.close();
			inputStream.close();
            FileUtil.unZip(ImageTagUtils.getUnZipFile(bookId, period,bookName),file);
			String fileMd5Code = FileUtil.fileMd5Code(file);
			if (fileMd5Code.equals(book.getMd5Code())) {
				imageTagService.updateBookStatus(bookId, period, bookName,  ImageTagSendFileUtil.FILE_SEND_STATUS_SENDED);
			} else {
				imageTagService.updateBookStatus(bookId, period, bookName,  ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND);
				System.err.println("++++++++++接收文件失败（imagetag）， 错误原因：传输过程中存在丢包。" );
			}
		} catch (IOException e) {
			System.out.println("++++++++++++++++");
			imageTagService.updateBookStatus(bookId, period, bookName,  ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND);
			System.err.println("++++++++++接收文件失败（imagetag）， 错误原因："+ e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.err.println("inputStream close IOException:" + e.getMessage());
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					System.err.println("fileOutputStream close IOException:" + e.getMessage());
				}
			}
		}
	}

    @RequestMapping(value="/receiveJsonFile")
    public void receiveJsonFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String bookId = request.getHeader("bookId");
        String sendBookJsonRecordId = request.getHeader("sendBookJsonRecordId");
        String period = request.getHeader("period");
        String bookName = request.getHeader("bookName");
        SendBookJsonRecordModel sendBookJsonRecordModel = imageTagService.getSendBookJsonRecordModel(Integer.valueOf(sendBookJsonRecordId));
        String path = ImageTagUtils.getBookJson(Integer.valueOf(bookId),bookName);
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        ServletInputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = request.getInputStream();
            fileOutputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int n = 0;
            while ((n=inputStream.read(b)) != -1) {
                fileOutputStream.write(b, 0, n);
            }
            fileOutputStream.close();
            inputStream.close();
            String fileMd5Code = FileUtil.fileMd5Code(file);
            if (fileMd5Code.equals(sendBookJsonRecordModel.getJsonCode())) {
                imageTagService.updateBookStatus(bookId, period, bookName,  ImageTagSendFileUtil.FILE_SEND_STATUS_SENDED);
            } else {
                imageTagService.updateBookStatus(bookId, period, bookName,  ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND);
                System.err.println("++++++++++Josn接收文件失败（imagetag）， 错误原因：传输过程中存在丢包。" );
            }
        } catch (IOException e) {
            System.out.println("++++++++++++++++");
            imageTagService.updateBookStatus(bookId, period, bookName,  ImageTagSendFileUtil.FILE_SEND_STATUS_UNSEND);
            System.err.println("++++++++++Josn接收文件失败（imagetag）， 错误原因："+ e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("inputStream close IOException:" + e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    System.err.println("fileOutputStream close IOException:" + e.getMessage());
                }
            }
        }
    }
}
