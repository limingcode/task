package com.teach.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.teach.dao.PersonFileDao;
import com.teach.dao.TpServersDao;
import com.teach.model.TpPersonFileSendRecord;

public class PersonFileThread extends Thread {

	private int fid;
	
	private int oaId;
	
	private String fileName;
	
	private String path;
	
	public PersonFileThread(int fid, int oaId, String fileName, String path) {
		super();
		this.fid = fid;
		this.oaId = oaId;
		this.fileName = fileName;
		this.path = path;
	}

	@Override
	public void run() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		TpServersDao serverDao = context.getBean(TpServersDao.class);
		PersonFileDao personFileDao = context.getBean(PersonFileDao.class);
		List<Map<String, Object>> serverList = serverDao.getPersonFileServerList(oaId);
		for (Map<String, Object> map : serverList) {
			int serverId = (Integer) map.get("id");
			String serverAddr = String.valueOf(map.get("serverAddr"));
			TpPersonFileSendRecord record = new TpPersonFileSendRecord(null, fid, serverId, serverAddr,
					PersonFileUtil.FILE_SEND_STATUS_SENDING, new Date(), 0);
			record = personFileDao.save(record);
			
			int code = PersonFileUtil.httpPost(serverAddr, SendFileUtil.getBytes(path), 
					fileName, oaId, record.getId());
			
			if (code != 200) {
				record.setStatus(PersonFileUtil.FILE_SEND_STATUS_UNSEND);
				personFileDao.save(record);
			}
		}
	}
}
