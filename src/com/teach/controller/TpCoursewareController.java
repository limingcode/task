package com.teach.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.teach.dao.TpCoursewareDao;
import com.teach.util.SendFileUtil;
import com.util.FileToZip;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teach.FileUploadMessage;
import com.teach.model.TpCourseware;
import com.teach.service.TpCoursewareService;
import com.teach.util.CommonUtil;
import com.teach.util.FileUploadUtil;

import net.sf.json.JSONObject;

/**
 * 保存、修改、查看ppt
 * @author huangdebin
 * @version 1.0
 */
@Controller
@RequestMapping("/tpCourseware")
public class TpCoursewareController {
	
	@Autowired
	private TpCoursewareService tpCoursewareService;
    @Resource
    private TpCoursewareDao tpCoursewareDao;
	/**
	 * toEditPpt
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/editPpt", method=RequestMethod.GET)
	public String editPpt(int lessonId, Model model) {
		TpCourseware courseware = tpCoursewareService.getCoursewareByLessonId(lessonId);
		model.addAttribute("courseware", courseware);
//		return "teach/courseware/editPPT";
		return "teach_v2/pptUpload";
	}
	
	/**
	 * toViewPpt
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toPreviewPpt", method=RequestMethod.GET)
	public String toPreviewPpt(int id, Model model) {
		TpCourseware courseware = tpCoursewareService.getCourseware(id);
		model.addAttribute("courseware", courseware);
		if(courseware.getPptType() == 2) {
			int lessonId = courseware.getLessonId();
			String unZipPath = FileUploadUtil.getCoursewareRealPath(lessonId, courseware.getPptType());
			List<String> list = CommonUtil.getFileNameByRegularExpression(unZipPath + "data", "thmb[0-9]{1,3}.*");
			String baseUrl = FileUploadUtil.getCoursewarePath(lessonId, courseware.getPptType()) + "data";
			model.addAttribute("baseUrl", baseUrl);
			model.addAttribute("list", list);
		}
		return "teach/courseware/previewPpt";	
	}
	
	/**
	 * 查询得到课件信息
	 * @param lessonId
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCourseware", method=RequestMethod.POST)
	public String getCourseware(int id) {
		TpCourseware courseware = tpCoursewareService.getCourseware(id);
		JSONObject json = JSONObject.fromObject(courseware);
		return json.toString();
	}
	
	/**
	 * 保存、修改ppt
	 * @param courseware
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveCourse", method=RequestMethod.POST)
	public String saveCourse(String id, String lessonId, String content, String animation, String aboveWords, String thumbnail, String[] background, HttpServletRequest request) {
		TpCourseware courseware = new TpCourseware();
		if (StringUtils.isNotEmpty(id)) {
			courseware = tpCoursewareService.getCourseware(Integer.parseInt(id));
		} else {
			courseware.setPptType((byte) 1);
			courseware.setLessonId(Integer.parseInt(lessonId));
			int oaId = (Integer) request.getSession().getAttribute("oaId");
			courseware.setOaId(oaId);
		}
		courseware.setContent(content);
		courseware.setAnimation(animation);
		courseware.setAboveWords(aboveWords);
		courseware.setThumbnail(thumbnail);
		courseware.setUpdateTime(new Date());
		courseware = tpCoursewareService.saveCourseware(courseware, background);
		return JSONObject.fromObject(courseware).toString();
	}
	
	/**
	 * 复制ppt
	 * @param currLessonId
	 * @param targetLessonId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/copyPpt", method=RequestMethod.POST)
	public String copyPpt(int currLessonId, int targetLessonId) {
		tpCoursewareService.saveCopyPpt(currLessonId, targetLessonId);
		FileUploadMessage message = new FileUploadMessage(true, "success");
		message.setMessage("success");
		JSONObject json = JSONObject.fromObject(message);
		return json.toString();
	}
//    @ResponseBody
//    @RequestMapping(value="/test", method=RequestMethod.GET)
//    public String test(String flag){
//        if ("huangrundong".equals(flag)) {
//            List<Map<String,Object>>  s= tpCoursewareDao.test();
//            for(Map<String,Object> s1:s){
//                Integer lessonId=  Integer.valueOf(s1.get("lessonId").toString());
//                 String url=FileUploadUtil.getCoursewareZip(Integer.valueOf(s1.get("lessonId").toString()));
//                //System.out.println(url);
//                File file =new File(url);
//                if(file.exists()){
//                    //if("1678".equals(s1.get("lessonId").toString())){
//                        System.out.println("文件存在啊"+url);
//                        CommonUtil.unZip(FileUploadUtil.getCoursewareRealPathZIP(lessonId, (byte) 5), file);
//                        FileToZip.copy(FileUploadUtil.getCommonFile(), FileUploadUtil.getCoursewareRealPathZIP(lessonId, (byte)6));
//                        FileToZip.copy(FileUploadUtil.getUploadCoursewareFileDir(1), FileUploadUtil.getCoursewareRealPathZIP(lessonId, (byte)3));
//                        FileToZip.copy(FileUploadUtil.getCoursewareRealPathZIP(lessonId, (byte)5), FileUploadUtil.getCoursewareRealPathZIP(lessonId, (byte)7));
//                        //压缩包存放的路径
//                        String zipFilePath= FileUploadUtil.getCoursewareZipV1(lessonId);
//                        //被压缩的文件路径
//                        String beZipFilePath= FileUploadUtil.getCoursewareRealPathZIP(lessonId, (byte) 6);
//                        //打成压缩包
//                        String filePath = SendFileUtil.packCoursewareFile(zipFilePath, beZipFilePath);
//                        System.out.println("完成打包"+filePath);
//                        FileUploadUtil.deleteDirectory(FileUploadUtil.getCoursewareRealPathZIP(lessonId, (byte)4));
//                    }
//                //}
//            }
//        }
//        return "这个接口不是你能调的";
//    }
	/**
	 * 上传ppt
	 * @param uploadFile
	 * @param lessonId
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/uploadCoureware", method=RequestMethod.POST)
	public String uploadCoureware(MultipartFile uploadFile, int lessonId, HttpServletRequest request) throws IllegalStateException, IOException {
        String uploadPath = FileUploadUtil.getCoursewareZipPath(lessonId);
        String unZipPath = FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 2);
        String unZipPathv1 = FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 4);
        FileUploadUtil.deleteDirectory(unZipPath);
        FileUploadUtil.deleteDirectory(unZipPathv1);
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(FileUploadUtil.getCoursewareZip(lessonId));
        //上传文件
        uploadFile.transferTo(file);
        //解压文件
        CommonUtil.unZip(unZipPath, file);
        CommonUtil.unZip(FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 3), file);
        CommonUtil.unZip(FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 9), file);
        List<String> list = CommonUtil.getFileNameByRegularExpression(unZipPath + "data", "thmb[0-9]{1,3}.*");
        if (list == null || list.size() == 0) { //上传的zip包文件目录不正确
            //删除数据
            tpCoursewareService.deleteUploadCoursewareFile(null, lessonId);
            FileUploadMessage message = new FileUploadMessage(false, "您上传的zip包文件内容不正确，请重新上传！");
            JSONObject json = JSONObject.fromObject(message);
            return json.toString();
        }
        saveUploadCoureware(null, lessonId, null, uploadFile.getOriginalFilename(), request);
        FileUploadMessage message = new FileUploadMessage(true, "successful");
        message.setMessage(uploadFile.getOriginalFilename());
        message.setUrl(FileUploadUtil.getCoursewarePath(lessonId, (byte) 2) + "data");
        message.setList(list);
        JSONObject json = JSONObject.fromObject(message);
        return json.toString();
    }

	/**
	 * 查询上传的ppt
	 * @param lessonId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUploadCourseware", method=RequestMethod.POST)
	public String getUploadCourseware(int lessonId) {
		String unZipPath = FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 2) + "data";
		List<String> list = CommonUtil.getFileNameByRegularExpression(unZipPath, "thmb[0-9]{1,3}.*");
		FileUploadMessage message = new FileUploadMessage(true, "successful");
		//ppt加载url
		message.setUrl(FileUploadUtil.getCoursewarePath(lessonId, (byte) 2) + "data");
		message.setList(list);
		JSONObject json = JSONObject.fromObject(message);
		return json.toString();
	}

	/**
	 * 保存上传的ppt
	 * @param id
	 * @param lessonId
	 * @param aboveWords
	 * @param fileName
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveUploadCoureware", method=RequestMethod.POST)
	public String saveUploadCoureware(String id, int lessonId, String aboveWords, String fileName, HttpServletRequest request){
		TpCourseware courseware = tpCoursewareService.getCoursewareByLessonId(lessonId);
		boolean isUpdate = true;
		if(courseware != null) {
			if (StringUtils.isNotEmpty(courseware.getUploadPptName())) {
				isUpdate = false;
			}
		} else {
			courseware = new TpCourseware();
			courseware.setLessonId(lessonId);
			courseware.setCreatTime(new Date());
		}
		int oaId = (Integer) request.getSession().getAttribute("oaId");
		courseware.setOaId(oaId);
		courseware.setUploadPptName(fileName);
		courseware.setPptType((byte) 2);
		courseware.setPageAboveWords(aboveWords);
		courseware.setUpdateTime(new Date());
		courseware = tpCoursewareService.saveUploadCourseware(courseware,isUpdate);
		
		JSONObject json = JSONObject.fromObject(courseware);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadVideoSrc", method=RequestMethod.POST)
	public String uploadVideoSrc(int lessonId, byte type, String videoSrc) {
		tpCoursewareService.uploadVideoSrc(lessonId, type, videoSrc);
		FileUploadMessage message = new FileUploadMessage(true, "提交成功！");
		JSONObject json = JSONObject.fromObject(message);
		return json.toString();
	}
	
	/**
	 * 删除上传的ppt
	 * @param id
	 * @param lessonId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteUploadCoursewareFile", method=RequestMethod.POST)
	public String deleteUploadCoursewareFile(String id, int lessonId) {
		//删除数据
		tpCoursewareService.deleteUploadCoursewareFile(id,lessonId);;
		
		FileUploadMessage message = new FileUploadMessage(true, "删除成功！");
		JSONObject json = JSONObject.fromObject(message);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/updateLessonName", method=RequestMethod.POST)
	public FileUploadMessage updateLessonName(int lessonId, String name) {
		tpCoursewareService.updateLessonName(lessonId, name);
		return new FileUploadMessage(true, "操作成功");
				
	}
	
}
