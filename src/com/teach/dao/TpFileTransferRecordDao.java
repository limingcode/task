package com.teach.dao;

import java.util.List;
import java.util.Map;

import com.teach.model.TpFileTransferRecord;

public interface TpFileTransferRecordDao {

	public int saveFileTransferLog(TpFileTransferRecord fileTransferRecord);
	
	public void updateTransferStatus(int id, byte status);

	public void updateCancelSendFile(int lessonId);
	
	public TpFileTransferRecord getRecord(int lessonId, byte type, int serverId);
	
	public void updateTransferStatus(int serverId, int lessonId, byte type, byte status);

	public void updateTransferStatus(TpFileTransferRecord record);

	public List<Map<String, Object>> getListByLessonId(int lessonId);

	public void uploadRecordDeleteStutas(int lessonId, byte type);

	public void deleteRecord();
	
}
