package com.teach.dao;

import java.util.List;
import java.util.Map;

import com.teach.Page;
import com.teach.model.TpPersonFile;
import com.teach.model.TpPersonFileSendRecord;

public interface PersonFileDao {
	
	public TpPersonFile getPersonFile(int id);
	
	TpPersonFileSendRecord getPersonFileSendRecord(int id);

	public boolean checkIsTeacher(int oaId);
	
	public Page getPersonFileList(int oaId, String search, Page page);
	
	public int save(TpPersonFile personFile);
	
	public TpPersonFileSendRecord save(TpPersonFileSendRecord record);
	
	public void delete(int id);
	
	public List<Map<String, Object>> getAllCourse(int oaId);
	
	public List<Map<String, Object>> getStudentList(int courseId);

	public void deleteRecord();

	TpPersonFileSendRecord getPersonFileSendRecord(int fid, String serverIp);
}
