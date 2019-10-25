package com.teach.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teach.FileUploadMessage;
import com.teach.dao.FileUploadDao;
import com.teach.dao.TpCoursewareDao;
import com.teach.dao.TpFileTransferRecordDao;
import com.teach.model.TpCourseware;
import com.teach.model.TpFileUpload;
import com.teach.service.FileUploadService;
import com.teach.util.FileUploadUtil;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Resource
	private FileUploadDao fileUploadDao;
	@Resource
	private TpFileTransferRecordDao fileTransferRecordDao;
	@Resource
	private TpCoursewareDao coursewareDao;
	
	/**
	 * 保存文件上传记录
	 */
	@Transactional
	public TpFileUpload saveUploadInfo(TpFileUpload fileUpload) {
		int lessonId = fileUpload.getLessonId();
		Byte fileType = fileUpload.getFileType();
		if(5 == fileType || 6 == fileType) {//说课、实录视频, 
			
			StringBuffer sql = new StringBuffer("update Tp_Courseware set ");
			if (5 == fileType) {
				sql.append(" lessonVideoUrl = null ");
			} else {
				sql.append(" memoirVideoUrl = null ");
			}
			sql.append(" where lessonId = "+ lessonId +"");
			fileUploadDao.updateCoursewareVideoUrl(sql.toString());   //更新课件视频地址
			
			//查询出是否上传过说课、实录视频 
			TpFileUpload videoFile = fileUploadDao.getVideoFile(lessonId, fileType);
			if(videoFile != null) {  //以前上传过说课、实录视频 
				FileUploadUtil.deleteFile(videoFile.getFileUrl()); //删除磁盘文件
				fileUploadDao.deleteFile(new Integer[] {videoFile.getId()});
				fileTransferRecordDao.uploadRecordDeleteStutas(lessonId, fileType);
			}
		}
		
		//背景图片上传
//		if(7 == fileType) {
//			TpFileUpload themeFile = fileUploadDao.getVideoFile(lessonId, fileType);
//			if(themeFile != null) { //删除原来的背景图片及记录
//				FileUploadUtil.deleteFile(themeFile.getFileUrl()); //删除磁盘文件
//				fileUploadDao.deleteFile(new Integer[] {themeFile.getId()});
//			}
//		}
		
		return fileUploadDao.saveUploadInfo(fileUpload);
	}
	
	@Override
	public TpFileUpload getFileByLessonIdAndFileType(int lessonId, byte fileType) {
		return fileUploadDao.getVideoFile(lessonId, fileType);
	}

	@Override
	public FileUploadMessage getIsSupportFileType(String fileName, byte fileType) {
		if (1 == fileType || 7 == fileType) { //图片上传
			if(!FileUploadUtil.isImageType(fileName)) {
				return new FileUploadMessage(false, "该图片格式不支持上传!");
			}
		} else if (2 == fileType) { //音频上传
			if(!FileUploadUtil.isAudioType(fileName)) {
				return new FileUploadMessage(false, "该音频格式不支持上传!");
			}
		} else if (3 == fileType) {
			if(!FileUploadUtil.isFlushType(fileName)) {
				return new FileUploadMessage(false, "该动画格式不支持上传!");
			}
		} else {
			if(!FileUploadUtil.isVideoType(fileName)) {
				return new FileUploadMessage(false, "该视频格式不支持上传!");
			}
		}
		
		return new FileUploadMessage(true, null);
	}

	/**
	 * 删除视频
	 */
	@Override
	public void deleteVideo(int lessonId, byte type) {
		TpFileUpload videoFile = fileUploadDao.getVideoFile(lessonId, type);
		if (videoFile == null) { //上传记录为空，为上传的url
			TpCourseware courseware = coursewareDao.getCoursewareByLessonId(lessonId);
			if (courseware != null) { 
				//未上传ppt，且未上传另外的视频url直接删除课次记录
				if (courseware.getPptType() == 0 || 
						type == 5 ? StringUtils.isEmpty(courseware.getMemoirVideoUrl()) : StringUtils.isEmpty(courseware.getLessonVideoUrl())) { 
					coursewareDao.deleteCourseware(courseware.getId());
				} else { //已上传ppt课件、或已上传另外的视频url
					if (type == 5) { //说课视频url置为空
						courseware.setLessonVideoUrl(null);
					} else {  //实录视频url置为空
						courseware.setMemoirVideoUrl(null);
					}
					coursewareDao.saveCourseware(courseware);
				}
			}
		} else {
			String dir = FileUploadUtil.getUploadHomePath() + FileUploadUtil.getUploadPath(type, lessonId);
			FileUploadUtil.deleteDirectory(dir);
			fileUploadDao.deleteFile(new Integer[] {videoFile.getId()});
			fileTransferRecordDao.uploadRecordDeleteStutas(lessonId, type);
		}
	}
	
}
