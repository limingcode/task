package com.teach.util;

import java.io.DataOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sun.net.www.protocol.http.HttpURLConnection;

public class PersonFileUtil {
	
	public static String PERSON_FILE_HOME_PATH = File.separator + "upload" + File.separator + "personFile" + File.separator;

	public static final String SEND_FILE_ADDR = "http://serverAddr/teach/personFile/fileReceive";
	
	/**
	 * 文件发送状态 - 发送中 - 1
	 */
	public static final byte FILE_SEND_STATUS_SENDING = 1;
	/**
	 * 文件发送状态 - 发送成功 - 2
	 */
	public static final byte FILE_SEND_STATUS_SENDED = 2;
	/**
	 * 文件发送状态 - 取消发送 - 3
	 */
	public static final byte FILE_SEND_STATUS_CANCELSEND = 3;
	/**
	 * 文件发送状态 - 发送失败 - 4
	 */
	public static final byte FILE_SEND_STATUS_UNSEND = 4;
	
	/**
	 * 得到tomcat webapps目录
	 * @return
	 */
	public static String getHomePath() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return System.getProperty("user.dir") + File.separator + ".." + File.separator + "webapps"; 
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession().getServletContext().getRealPath("") + File.separator + "..";  
	}
	
	/**
	 * 得到个人文件夹路径
	 * @return
	 */
	public static String getPersonDirPath(int oaId) {
		return PERSON_FILE_HOME_PATH + oaId + File.separator;
	}
	
	public static boolean getFileIsExist(int oaId, String fileName) {
		String filePath = getHomePath() + getPersonDirPath(oaId) + fileName;
		File file = new File(filePath);
		return file.exists();
	}
	
	public static boolean isZipType(String fileName) {
		return StringUtils.endsWithIgnoreCase(fileName, ".zip");
	}
	
	/** 
     * 将文件流发送至另外服务器的方法（这里有个fileName 
	 * @param serverIp 
     * @param bytes 
     * @param fileName 
	 * @return 
     * @return 从服务器端 响应的流 可通过 new String(bytes); 转换 
     */  
    public static int httpPost(String serverIp, byte[] bytes, String fileName, int oaId, int id) {  
        try {
            String url = SEND_FILE_ADDR.replace("serverAddr", serverIp);  
            URL console = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) console  
                    .openConnection();  
            conn.setConnectTimeout(30000);  
            conn.setReadTimeout(30000);  
            conn.setUseCaches(false);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setRequestMethod("POST");
            conn.addRequestProperty("oaId", String.valueOf(oaId));
            conn.addRequestProperty("id", String.valueOf(id));
            conn.addRequestProperty("fileName", URLEncoder.encode(fileName,"UTF-8"));
            conn.connect();  
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());  
            out.write(bytes);  
            // 刷新、关闭  
            out.flush(); 
            out.close();  
            return conn.getResponseCode();
        } catch (Exception e) { 
        	System.out.println("+++++++++++++ (personFile)发送文件到  "+ StringUtils.substring(serverIp, 0, StringUtils.indexOf(serverIp, ":")) +" 失败  +++++++++++++");
        	return 400;  
        }  
    }
	
    public static long getDirTotalSize(String dirPath) {
    	File file = new File(dirPath);
    	if (file.isFile()) {
			return file.length();
		}
    	long total = 0;
    	File[] listFiles = file.listFiles();
    	if (listFiles != null) {
    		for (File file2 : listFiles) {
    			total += getDirTotalSize(file2.getPath());
    		}
		}
    	return total;
    }
    
}
