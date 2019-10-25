package com.teach.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.teach.dao.FileUploadDao;
import com.teach.dao.TpFileTransferRecordDao;
import com.teach.model.TpFileTransferRecord;
import com.teach.model.TpFileUpload;
import com.teach.service.TpFileTransferRecordService;
import com.teach.util.FileUploadUtil;
import com.teach.util.SendFileUtil;

@Service
public class TpFileTransferRecordServiceImpl implements TpFileTransferRecordService {

	@Resource
	private TpFileTransferRecordDao recordDao;
	@Resource
	private FileUploadDao fileUploadDao;
	
	
	/**
	 * 发送单个文件到指定服务器
	 */
	@Override
	public void sendFileToServer(int serverId, String serverAddr, int lessonId, byte type) {
		// TODO Auto-generated method stub
		TpFileTransferRecord record = recordDao.getRecord(lessonId, type, serverId);
		String sendAddr = getFileRealPath(lessonId, type);
		if (record == null) {
			record = new TpFileTransferRecord();
			record.setLessonId(lessonId);
			record.setSendAddr(sendAddr);
			record.setType(type);
			record.setServerAddr(serverAddr);
			record.setServerId(serverId);
			record.setCreateTime(new Date());
		}
		SendFileUtil.httpPost(serverAddr, SendFileUtil.getBytes(sendAddr), 
				sendAddr.substring(sendAddr.lastIndexOf(File.separatorChar)), lessonId, type);
		int count = record.getCountNum() + 1;
		record.setCountNum(count);
		record.setStatus(SendFileUtil.FILE_SEND_STATUS_SENDING);
		record.setUpdateTime(new Date());
		recordDao.updateTransferStatus(record);
	}

	/**
	 * 得到正在发送的文件列表
	 */
	@Override
	public List<Map<String, Object>> getListByLessonId(int lessonId) {
		// TODO Auto-generated method stub
		return recordDao.getListByLessonId(lessonId);
	}
	
	/**
	 * 根据类型得到文件路径
	 * @param lessonId
	 * @param type
	 * @return
	 */
	public String getFileRealPath(int lessonId, byte type) {
		String filePath = "";
		if (type == 1) {
			filePath = FileUploadUtil.getCoursewareZip(lessonId);
		} else {
			TpFileUpload fileUpload = fileUploadDao.getVideoFile(lessonId, type);
			filePath = FileUploadUtil.getUploadHomePath() + fileUpload.getFileUrl();
		}
		File file = new File(filePath);
		return file.getPath();
	}
}
