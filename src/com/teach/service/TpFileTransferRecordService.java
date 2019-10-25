package com.teach.service;

import java.util.List;
import java.util.Map;

public interface TpFileTransferRecordService {

	public void sendFileToServer(int serverId,  String serverAddr, int lessonId, byte type);
	
	public List<Map<String, Object>> getListByLessonId(int lessonId);
}
