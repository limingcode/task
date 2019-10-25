package com.teach.util;

import java.io.File;
import java.util.Date;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.teach.dao.TpFileTransferRecordDao;
import com.teach.model.TpFileTransferRecord;

public class SendFileThread extends Thread {
	
	private int serverId;

	private String serverIp;
	
	private int lessonId;
	
	private String zipFilePath;
	
	private byte type;
	
	private int oaId;
	
	public SendFileThread(int serverId, String serverIp, int lessonId, String zipFilePath, byte type, int oaId) {
		super();
		this.serverId = serverId;
		this.serverIp = serverIp;
		this.lessonId = lessonId;
		this.zipFilePath = zipFilePath;
		this.type = type;
		this.oaId = oaId;
	}


	@Override
	public void run() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		TpFileTransferRecordDao fileTransferRecordDao = context.getBean(TpFileTransferRecordDao.class);
		TpFileTransferRecord fileTransferRecord = fileTransferRecordDao.getRecord(lessonId, type, serverId);
		if(fileTransferRecord == null) {
			fileTransferRecord = new TpFileTransferRecord();
			fileTransferRecord.setLessonId(lessonId);
			fileTransferRecord.setType(type);
			fileTransferRecord.setServerId(serverId);
			fileTransferRecord.setServerAddr(serverIp);
			fileTransferRecord.setOaId(oaId);
			fileTransferRecord.setCreateTime(new Date());
		}
		fileTransferRecord.setIsDelete((byte) 0);
		fileTransferRecord.setCountNum(0);   //集体发送时次数变为0
		fileTransferRecord.setSendAddr(zipFilePath);
		fileTransferRecord.setStatus(SendFileUtil.FILE_SEND_STATUS_SENDING);
		fileTransferRecord.setUpdateTime(new Date());
		
		int id = fileTransferRecordDao.saveFileTransferLog(fileTransferRecord);
		
		int code = SendFileUtil.httpPost(serverIp, SendFileUtil.getBytes(zipFilePath), 
				zipFilePath.substring(zipFilePath.lastIndexOf(File.separatorChar)+1), lessonId, type);
		if(code != 200) {
			fileTransferRecordDao.updateTransferStatus(id, SendFileUtil.FILE_SEND_STATUS_UNSEND);
		}
	}
	
}
