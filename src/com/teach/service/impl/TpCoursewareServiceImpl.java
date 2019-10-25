package com.teach.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teach.dao.FileUploadDao;
import com.teach.dao.TpCoursewareDao;
import com.teach.dao.TpCoursewareRandomDao;
import com.teach.dao.TpFeedbackDao;
import com.teach.dao.TpFileTransferRecordDao;
import com.teach.dao.TpServersDao;
import com.teach.model.TpCourseware;
import com.teach.model.TpCoursewareRandom;
import com.teach.model.TpFileUpload;
import com.teach.service.TpCoursewareService;
import com.teach.util.CommonUtil;
import com.teach.util.FileUploadUtil;
import com.teach.util.SendFileUtil;
import com.util.FileToZip;

@Service
public class TpCoursewareServiceImpl implements TpCoursewareService {

	@Resource
	private TpCoursewareDao tpCoursewareDao;
	@Resource
	private TpCoursewareRandomDao tpCoursewareRandomDao;
	@Resource
	private FileUploadDao fileUploadDao;
	@Resource
	private TpFileTransferRecordDao fileTransferRecordDao;
	@Resource
	private TpServersDao serverDao;
	@Resource
	private TpFeedbackDao feedbackDao;
	
	/**
	 * 保存ppt课件
	 */
	@Override
	@Transactional
	public TpCourseware saveCourseware(TpCourseware courseware, String[] background) {
//		int lessonId = courseware.getLessonId();
		courseware = tpCoursewareDao.saveCourseware(courseware);
//		updateUploadFile(courseware.getLessonId(), courseware.getContent(), background);
//		SendFileUtil.sendFile(lessonId, (byte) 1, "", courseware.getOaId());
		return courseware;
	}

	/**
	 * 更新上传的文件
	 */
	@Override
	@Transactional
	public void updateUploadFile(int lessonId, String content, String[] background) {
		List<Serializable> fileList = fileUploadDao.getFileListByLessonId(lessonId);
		//fileList不为空，说明该课件有添加多媒体
		if (fileList != null && fileList.size() > 0) {
			Pattern pattern = Pattern.compile(FileUploadUtil.FILE_UPLOAD_PATH_REGULAR_EXPRESSION, Pattern.CASE_INSENSITIVE);
			Matcher dateMatcher = pattern.matcher(content);
			List<String> list = new ArrayList<String>();
			while(dateMatcher.find()) {   //找出所有匹配正则表达式的src值，并加到list
				list.add(dateMatcher.group());  
			}
			CommonUtil.removeDuplicate(list);
			for (String str : list) {
				TpFileUpload fileUpload = null;
				for (Serializable serializable : fileList) {
					fileUpload = (TpFileUpload)serializable;
					String fileUrl = fileUpload.getFileUrl();
					if(StringUtils.equals(str, fileUrl)) {
						fileList.remove(serializable);
						break;
					} 
				}
			}
			//fileList.size > 0 说明课件进行了多媒体修改，则需要删除文件及记录
			if(fileList.size() > 0) {
				Integer[] ids = new Integer[fileList.size()];
				String[] fileUrls = new String[fileList.size()];
				for (int i = 0; i < fileList.size(); i++) {
					TpFileUpload fileUpload = (TpFileUpload) fileList.get(i);
					ids[i] = fileUpload.getId();
					fileUrls[i] = fileUpload.getFileUrl();
				}
				//删除数据库记录
				fileUploadDao.deleteFile(ids);
				//删除磁盘文件
				FileUploadUtil.deleteFile(fileUrls);
			}
		} 
		
		//上传主题处理
		List<Serializable> themeFileList = fileUploadDao.getThemeFileList(lessonId);
		for (String string : background) {
			for (Serializable serializable : themeFileList) {
				TpFileUpload fileUpload = (TpFileUpload) serializable;
				if(StringUtils.contains(string, fileUpload.getFileUrl())) {
					themeFileList.remove(serializable);
					break;
				}
			}
		}

		if(themeFileList.size() > 0) {
			Integer[] ids = new Integer[themeFileList.size()];
			String[] fileUrls = new String[themeFileList.size()];
			for (int i = 0; i < themeFileList.size(); i++) {
				TpFileUpload fileUpload = (TpFileUpload) themeFileList.get(i);
				ids[i] = fileUpload.getId();
				fileUrls[i] = fileUpload.getFileUrl();
			}
			//删除数据库记录
			fileUploadDao.deleteFile(ids);
			//删除磁盘文件
			FileUploadUtil.deleteFile(fileUrls);
		}
	}

	/**
	 * 通过id得到课件
	 */
	public TpCourseware getCourseware(int id) {
		return tpCoursewareDao.getCourseware(id);
	}
	
	/**
	 * 通过lessonId得到课件 
	 */
	@Override
	public TpCourseware getCoursewareByLessonId(int lessonId) {
		return tpCoursewareDao.getCoursewareByLessonId(lessonId);
	}

	/**
	 * ppt复制
	 */
	@Override
	@Transactional
	public void saveCopyPpt(int currLessonId, int targetLessonId) {
		Map<String, Object> courseware = tpCoursewareDao.getCoursewareMapByLessonId(currLessonId);
		if(courseware != null && courseware.size() > 0) {
			courseware.remove("id");
			courseware.put("lessonId", targetLessonId);
			courseware.put("content", StringUtils.replace((String) courseware.get("content"),
					"/"+ currLessonId +"/", "/"+ targetLessonId +"/"));
			courseware.put("lessonVideoUrl", StringUtils.replace((String) courseware.get("lessonVideoUrl"), 
					"/"+ currLessonId +"/", "/"+ targetLessonId +"/"));
			courseware.put("memoirVideoUrl", StringUtils.replace((String) courseware.get("memoirVideoUrl"), 
					"/"+ currLessonId +"/", "/"+ targetLessonId +"/"));
			courseware.put("background", StringUtils.replace((String) courseware.get("background"), 
					"/"+ currLessonId +"/", "/"+ targetLessonId +"/"));
			tpCoursewareDao.saveCourseware("TP_Courseware", courseware);
		}
		
		Map<String, Object> lessonVideo = null, memoirVideo = null;
		List<Map<String, Object>> fileList = fileUploadDao.getAllFileList(currLessonId);
		if(fileList != null && fileList.size() > 0)
		for (Map<String, Object> map : fileList) {
			map.remove("id");
			map.put("lessonId", targetLessonId);
			map.put("fileUrl", StringUtils.replace((String) map.get("fileUrl"), 
					"/"+ currLessonId +"/", "/"+ targetLessonId +"/"));
			if((byte)5 == (Byte)map.get("fileType")) {
				lessonVideo = map;
			}else if (6 == (Byte)map.get("fileType")) {
				memoirVideo = map;
			}
		}
		fileUploadDao.saveUploadInfoList("TP_FileUpload", fileList);
		
		//磁盘文件复制
		SendFileUtil.copy(currLessonId, targetLessonId, (byte) 1);
		if(lessonVideo != null || memoirVideo != null) {
			SendFileUtil.copy(currLessonId, targetLessonId, (byte) 5);
		}
		
		//文件发送
		SendFileUtil.sendFile(targetLessonId, (byte) 1, "", (Integer) courseware.get("oaId"));
		if(lessonVideo != null) {
			SendFileUtil.sendFile(targetLessonId, (byte) 5, FileUploadUtil.getUploadHomePath() + (String) lessonVideo.get("fileUrl"), (Integer) courseware.get("oaId"));
		}
		if(memoirVideo != null) {
			SendFileUtil.sendFile(targetLessonId, (byte) 6, FileUploadUtil.getUploadHomePath() + (String) memoirVideo.get("fileUrl"), (Integer) courseware.get("oaId"));
		}
	}

	/**
	 * 保存通过上传方式产生的ppt
	 */
	@Override
	@Transactional
	public TpCourseware saveUploadCourseware(TpCourseware courseware, boolean isUpdate) {
        //文件复制
        int lessonId = courseware.getLessonId();
        courseware = tpCoursewareDao.saveCourseware(courseware);
        if (isUpdate) {
            //把文件复制到那个路径
            String path = FileUploadUtil.getCoursewareRealPath(courseware.getLessonId(), (byte) 3);
            String indexHtml = path + "/index.html";
            String index = CommonUtil.readFileByLines(indexHtml);
            //拿到要复制的文件
            String src = FileUploadUtil.getUploadCoursewareFileDir(StringUtils.contains(index, "version"));
            //复制文件
            FileToZip.copy(src, path);
            //压缩包存放的路径
            String zipFilePath= FileUploadUtil.getCoursewareZip(lessonId);
            //被压缩的文件路径
            String beZipFilePath= FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 5);
            //打成压缩包
            String filePath = SendFileUtil.packCoursewareFile(zipFilePath, beZipFilePath);
            FileToZip.copy(FileUploadUtil.getCommonFile(), FileUploadUtil.getCoursewareRealPath(courseware.getLessonId(), (byte)6));
            FileToZip.copy(FileUploadUtil.getUploadCoursewareFileDir(1), FileUploadUtil.getCoursewareRealPath(courseware.getLessonId(), (byte)9));
            FileToZip.copy(FileUploadUtil.getCoursewareRealPath(courseware.getLessonId(), (byte)10), FileUploadUtil.getCoursewareRealPath(courseware.getLessonId(), (byte)7));

            TpCoursewareRandom coursewareRandom = tpCoursewareRandomDao.getCoursewareRandomByLessonId(lessonId);
            if (coursewareRandom == null) {
                coursewareRandom = new TpCoursewareRandom(null, lessonId, (int) (Math.random() * 10000));
                tpCoursewareRandomDao.saveCoursewareRandom(coursewareRandom);
            } else {
                coursewareRandom.setRandomNum((int) (Math.random() * 10000));
                tpCoursewareRandomDao.saveCoursewareRandom(coursewareRandom);
            }
            SendFileUtil.sendFile(lessonId, (byte) 1, filePath, courseware.getOaId());
        }
        return courseware;
    }

    /*@Override
    public TpCourseware saveUploadCourseware(TpCourseware courseware, boolean isUpdate) {
        //文件复制
        int lessonId = courseware.getLessonId();
        courseware = tpCoursewareDao.saveCourseware(courseware);
        if(isUpdate) {
            String path = FileUploadUtil.getCoursewareRealPath(courseware.getLessonId(), (byte) 1);
            String src=FileUploadUtil.getCommonFile();
            FileToZip.copy(src, path);

            TpCoursewareRandom coursewareRandom = tpCoursewareRandomDao.getCoursewareRandomByLessonId(lessonId);
            if (coursewareRandom == null) {
                coursewareRandom = new TpCoursewareRandom(null, lessonId, (int)(Math.random()*10000));
                tpCoursewareRandomDao.saveCoursewareRandom(coursewareRandom);
            } else {
                coursewareRandom.setRandomNum((int)(Math.random()*10000));
                tpCoursewareRandomDao.saveCoursewareRandom(coursewareRandom);
            }

            SendFileUtil.sendFile(lessonId, (byte) 1, "", courseware.getOaId());
        }
        return courseware;
    }*/

    /**
	 * 删除上传的ppt
	 */
	@Override
	@Transactional
	public void deleteUploadCoursewareFile(String id, int lessonId) {
		String coursewarePath = FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 2);
		String zipPath = FileUploadUtil.getCoursewareZipPath(lessonId);
		TpCourseware courseware = tpCoursewareDao.getCoursewareByLessonId(lessonId);
		if(StringUtils.isNotEmpty(id) || courseware != null) {  //已保存到数据库，对保存的数据进行处理  //pptType 0-未上传课件，只上传了说课实录、视频。1-编辑的ppt。 2-上传的ppt
			if (StringUtils.isEmpty(courseware.getContent()) &&
				StringUtils.isEmpty(courseware.getLessonVideoUrl()) &&
				StringUtils.isEmpty(courseware.getMemoirVideoUrl())) { 
				tpCoursewareDao.deleteCourseware(courseware.getId());
				tpCoursewareRandomDao.deleteCoursewareRandom(lessonId);
				fileTransferRecordDao.uploadRecordDeleteStutas(lessonId, (byte) 1);
			} else {
				if (StringUtils.isNotEmpty(courseware.getContent())) {
					courseware.setPageAboveWords(null);
					courseware.setUploadPptName(null);
					courseware.setPptType((byte) 1);
					courseware.setUpdateTime(new Date());
					tpCoursewareDao.saveCourseware(courseware);
					SendFileUtil.sendFile(lessonId, (byte) 1, "", courseware.getOaId());
				} else {
					courseware.setPptType((byte) 0);
					courseware.setPageAboveWords(null);
					courseware.setUploadPptName(null);
					courseware.setUpdateTime(new Date());
					tpCoursewareRandomDao.deleteCoursewareRandom(lessonId);
					tpCoursewareDao.saveCourseware(courseware);
				}
			}
		}
		FileUploadUtil.deleteDirectory(coursewarePath, zipPath);//删除上传的zip和解压出来的文件夹
	}

	/**
	 * 课次删除
	 */
	@Override
	@Transactional
	public void deleteCourseware(int lessonId) {
		tpCoursewareDao.deleteCoursewareBylessonId(lessonId);
		String coursewarePath = FileUploadUtil.getCoursewarePath(lessonId, (byte) 0);
		String coursewareZipPath = FileUploadUtil.getCoursewareZipPath(lessonId);
		FileUploadUtil.deleteDirectory(coursewarePath, coursewareZipPath); //课件文件，zip文件删除
		fileUploadDao.deleteFileByLessonId(lessonId);
		feedbackDao.deleteFeedbackByLessonId(lessonId);
		fileTransferRecordDao.uploadRecordDeleteStutas(lessonId, (byte) 1);
		fileTransferRecordDao.uploadRecordDeleteStutas(lessonId, (byte) 5);
		fileTransferRecordDao.uploadRecordDeleteStutas(lessonId, (byte) 6);
		feedbackDao.deleteAttendanceRecord(lessonId);
	}
	
	@Override
	@Transactional
	public void uploadVideoSrc(int lessonId, byte type, String videoSrc) {
		TpCourseware courseware = tpCoursewareDao.getCoursewareByLessonId(lessonId);
		if (courseware == null) {
			courseware = new TpCourseware();
			courseware.setLessonId(lessonId);
			courseware.setPptType((byte) 0);
		}
		if (5 == type) {
			courseware.setLessonVideoUrl(videoSrc);
		} else {
			courseware.setMemoirVideoUrl(videoSrc);
		}
		tpCoursewareDao.saveCourseware(courseware);
		TpFileUpload videoFile = fileUploadDao.getVideoFile(lessonId, type);
		if (videoFile != null) {
			FileUploadUtil.deleteFile(videoFile.getFileUrl()); //删除磁盘文件
			fileUploadDao.deleteFile(new Integer[] {videoFile.getId()});
			fileTransferRecordDao.uploadRecordDeleteStutas(lessonId, type);
		}
	}
	
	@Override
	public void updateLessonName(int id, String name) {
		tpCoursewareDao.updateLessonName(id, name);
	}
	
}
