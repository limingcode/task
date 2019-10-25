package com.image.tag.utils;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import com.util.CommonUtil;

import sun.net.www.protocol.http.HttpURLConnection;

public class ImageTagSendFileUtil {
	
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

	public static String FILE_SEND_ADDR = CommonUtil.APPFILEURL+"/AppTask/imageTag/receiveFile.jhtml?oaId=0";
    public static String FILE_SEND_ADDR_JSON = CommonUtil.APPFILEURL+"/AppTask/imageTag/receiveJsonFile.jhtml?oaId=0";
//	public static String FILE_SEND_ADDR = "http://localhost:8989/AppTask/imageTag/receiveFile.jhtml?oaId=0";
//	public static String FILE_SEND_ADDR_JSON = "http://localhost:8989/AppTask/imageTag/receiveJsonFile.jhtml?oaId=0";

	public static int httpPost(int sendBookJsonRecordId,int bookId, String period, byte[] bytes,String url,String bookName) {
        try {
            URL console = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) console  
                    .openConnection();  
            conn.setConnectTimeout(30000);  
            conn.setReadTimeout(30000);  
            conn.setUseCaches(false);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.addRequestProperty("bookId", String.valueOf(bookId));
            conn.addRequestProperty("sendBookJsonRecordId", String.valueOf(sendBookJsonRecordId));
            conn.addRequestProperty("period", period);
            conn.addRequestProperty("bookName", bookName);
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());  
            out.write(bytes);  
//             刷新、关闭  
            out.flush(); 
            out.close();  
            return conn.getResponseCode();
        } catch (Exception e) {
//        	e.printStackTrace();
        	System.out.println("+++++++++++++ 发送文件失败(imageTag),失败原因："+ e.getMessage());
        	return 400;  
        } 
	}

    public static int httpPost(int bookId, String period, byte[] bytes,String url) {
        try {
            URL console = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) console
                    .openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.addRequestProperty("bookId", String.valueOf(bookId));
            conn.addRequestProperty("period", period);
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(bytes);
//             刷新、关闭
            out.flush();
            out.close();
            return conn.getResponseCode();
        } catch (Exception e) {
//        	e.printStackTrace();
            System.out.println("+++++++++++++ 发送文件失败(imageTag),失败原因："+ e.getMessage());
            return 400;
        }
    }
	/**
     * 根据进程name查询进程
     * @param lessonId
     * @return
     */
    public static Thread findThread(String bookId, String bookName) {
    	Thread thread = null;
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group != null) {
            Thread[] threads = new Thread[(int)(group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for(int i = 0; i < count; i++) {
                if(bookId + "_" + bookName == threads[i].getName()) {
                	thread = threads[i];
                }
            }
            group = group.getParent();
        }
        return thread;
    }

    /**
     *
     * @param threadName
     * @return
     */
    public static Thread findThread(String threadName) {
        Thread thread = null;
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group != null) {
            Thread[] threads = new Thread[(int)(group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for(int i = 0; i < count; i++) {
                if(threadName.equals(threads[i].getName())) {
                    thread = threads[i];
                }
            }
            group = group.getParent();
        }
        return thread;
    }
    public static String getServerAddress() {
		// 根据网卡获取本机配置的IP地址
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ipAddress = inetAddress.getHostAddress();
		// 对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
		if (null != ipAddress && ipAddress.length() > 15) {
			// "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
    
}
