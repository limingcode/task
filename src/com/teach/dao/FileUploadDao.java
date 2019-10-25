package com.teach.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.teach.model.TpFileUpload;

public interface FileUploadDao {

	public TpFileUpload saveUploadInfo(TpFileUpload fileupload);
	
	public List<Serializable> getFileListByLessonId(int lessonId);

	public void deleteFile(Integer[] ids);

	public void updateCoursewareVideoUrl(String hql);

	public TpFileUpload getVideoFile(int lessonId, Byte fileType);
	
	public List<Serializable> getThemeFileList(int lessonId);
	
	public List<Map<String, Object>> getAllFileList(int lessonId);
	
	public void saveUploadInfoList(String tableName, List<Map<String, Object>> list);

	public void deleteFileByLessonId(int lessonId);

}
