package com.teach.service;

import com.teach.FileUploadMessage;
import com.teach.model.TpFileUpload;

public interface FileUploadService {

	public TpFileUpload saveUploadInfo(TpFileUpload fileUpload);

	public TpFileUpload getFileByLessonIdAndFileType(int lessonId, byte fileType);
	
	public FileUploadMessage getIsSupportFileType(String fileName, byte fileType);

	public void deleteVideo(int lessonId, byte type);
	
}
