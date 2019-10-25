package com.teach.dao;

import java.util.List;
import java.util.Map;

public interface TpServersDao {

	public List<Map<String, Object>> getServerList();
	
	public List<Map<String, Object>> getPersonFileServerList(int oaId);

	Map<String, Object> getServerByServerIp(String serverIp);
	
}
