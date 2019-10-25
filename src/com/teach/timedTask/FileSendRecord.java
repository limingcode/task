package com.teach.timedTask;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teach.dao.PersonFileDao;
import com.teach.dao.TpFileTransferRecordDao;

@Component
public class FileSendRecord {
	
	@Resource
	private TpFileTransferRecordDao fileTransferRecordDao;
	@Resource
	private PersonFileDao personFileDao;

	/**
	 * 每天执行一次
	 * @throws Exception 
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteFileRecord() {
		fileTransferRecordDao.deleteRecord();
	}
	
	/**
	 * 删除个人文件无效上传记录，每天执行一次
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void deletePersonFileRecord() {
		personFileDao.deleteRecord();
	}
	
}
