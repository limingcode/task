package com.teach.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teach.dao.TpServersDao;

@Repository
public class TpServersDaoImpl extends BaseDao implements TpServersDao {

	@Override
	public List<Map<String, Object>> getServerList() {
		String sql = "select id, (serverIp + ':' + serverPost) serverAddr from tp_servers where type = '2'";
//		String sql = "select id, (serverIp + ':' + serverPost) serverAddr from tp_servers where id = "+ 22;
		return getListUseSql(sql);
	}

	@Override
	public List<Map<String, Object>> getPersonFileServerList(int oaId) {
		String sql = "select id, (serverIp + ':' + serverPost) serverAddr from tp_servers where type = '2' "
				+ "and (code in (select distinct depa from edu_course where teacher = (select id from edu_teacher where oaId = "+ oaId +") and status = 1 and getdate() > beginDate and getdate()<= endDate) or code = 'CYLXQ')";
//		String sql = "select id, (serverIp + ':' + serverPost) serverAddr from tp_servers where id = "+ 22;
		return getListUseSql(sql);
	}
	
	@Override
	public Map<String, Object> getServerByServerIp(String serverIp){
		String sql = "select id, (serverIp + ':' + serverPost) serverAddr from tp_servers where serverIp = '"+ serverIp +"'";
		return getMapUseSql(sql);
	}

}
