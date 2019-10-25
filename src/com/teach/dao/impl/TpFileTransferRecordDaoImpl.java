package com.teach.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teach.dao.TpFileTransferRecordDao;
import com.teach.model.TpFileTransferRecord;
import com.teach.util.SendFileUtil;

@Repository
public class TpFileTransferRecordDaoImpl extends BaseDao implements TpFileTransferRecordDao {

	@Override
	public int saveFileTransferLog(TpFileTransferRecord fileTransferRecord) {
		// TODO Auto-generated method stub
		Serializable obj = saveOrUpdate(fileTransferRecord);
		return ((TpFileTransferRecord) obj).getId();
	}

	@Override
	public void updateTransferStatus(int id, byte status) {
		// TODO Auto-generated method stub
		String sql = "update TP_FileTransferRecord set status = '"+ status +"', updateTime = getDate() "
				+ "where id = '"+ id +"' ";
		updateUseSql(sql);
	}

	@Override
	public void updateCancelSendFile(int lessonId) {
		// TODO Auto-generated method stub
		String sql = "update TP_FileTransferRecord set status = '"+ SendFileUtil.FILE_SEND_STATUS_CANCELSEND +"', updateTime = getDate() "
				+ "where lessonId = '"+ lessonId +"' and status = '"+ SendFileUtil.FILE_SEND_STATUS_SENDING +"'";
		updateUseSql(sql);
		
	}

	@Override
	public TpFileTransferRecord getRecord(int lessonId, byte type, int serverId) {
		String hql = "from TpFileTransferRecord fr where fr.lessonId = '"+ lessonId +"' and fr.type = '"+ type +"' and serverId = '"+ serverId +"'";
		return (TpFileTransferRecord) getObjUseHql(hql);
	}

	@Override
	public void updateTransferStatus(int serverId, int lessonId, byte type, byte status) {
		String sql = "update TP_FileTransferRecord set status = '"+ status +"', updateTime = getDate() "
				+ "where serverId = '"+ serverId +"' and lessonId = '"+ lessonId +"' and type = '"+ type +"'";
		updateUseSql(sql);
	}

	@Override
	public void updateTransferStatus(TpFileTransferRecord record) {
		// TODO Auto-generated method stub
		update(record);
	}

	@Override
	public List<Map<String, Object>> getListByLessonId(int lessonId) {
		// TODO Auto-generated method stub
		String sql = "select id, serverId, type, status from TP_FileTransferRecord where lessonId = '"+ lessonId +"' and status = '1'";
		return getListUseSql(sql);
	}

	@Override
	public void uploadRecordDeleteStutas(int lessonId, byte type) {
		// TODO Auto-generated method stub
		String sql = "update TP_FileTransferRecord set isDelete = 1, updateTime = getDate() where type = "+ type +" and lessonId =" +lessonId;
		updateUseSql(sql);
	}

	@Override
	public void deleteRecord() {
		String sql = "delete from TP_FileTransferRecord where isDelete = 1 and datediff(day, updateTime, getDate()) > 7";
		updateUseSql(sql);
	}

}
