package com.teach.service;

import java.util.List;
import java.util.Map;

import com.teach.FileUploadMessage;
import com.teach.Page;
import com.teach.model.TpPersonFile;

public interface PersonFileService {
	
	boolean checkIsTeacher(int oaId);

	public Page getPersonFileList(int oaId, String search, Page page);

	public FileUploadMessage getIsSupportFileType(String fileName);

	public void savePersonFile(int oaId, TpPersonFile personFile);

	public FileUploadMessage reNameFile(int id, String name);
	
	public void deleteFile(int id);
	
	public List<Map<String, Object>> getAllCourse(int oaId);
	
	public List<Map<String, Object>> getStudentList(int courseId);

	public void sendFileToServer(int id);

	public void sendFile(int id, String serverIp);

}
