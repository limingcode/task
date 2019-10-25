package com.teach.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.teach.dao.TpFileTransferRecordDao;
import com.teach.dao.TpServersDao;
import com.util.FileToZip;

import sun.net.www.protocol.http.HttpURLConnection;

public class SendFileUtil {
	
	public static final String FILE_BASE_PATH = "/upload/teach/courseware/";
	public static final String FILE_BASE_PATH_zip = "/upload/teach/zip/";

	public static final String ZIP_PATH = "/upload/teach/zip/";
	
	public static final String VIDEO_PATH = "/upload/teach/video/";
	
	/**
	 * 文件发送类型 - ppt多媒体zip包 -1
	 */
	public static final byte SEND_FILE_TYPE_COURSEWARE = 1;
	/**
	 * 文件发送类型 - 说课视频 -2
	 */
	public static final byte SEND_FILE_TYPE_LESSONVIDEO = 2;
	/**
	 * 文件发送类型 - 实录视频 -3
	 */
	public static final byte SEND_FILE_TYPE_MEMOIRVIDEO = 3;
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
	 * 文件发送地址
	 */
	public static final String SEND_FILE_ADDR = "http://serverAddr/teach/fileUpload/fileReceive";
	/**
	 * 文件删除地址
	 */
	public static final String DELETE_FILE_ADDR = "http://serverAddr/teach/fileUpload/fileDelete";
	
	
	/**
	 * 发送文件
	 * @param lessonId
	 * @param oaId
	 */
	public static void sendFile(int lessonId, byte type, String filePath, int oaId) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        TpFileTransferRecordDao fileTransferRecordDao = context.getBean(TpFileTransferRecordDao.class);
        TpServersDao serverDao = context.getBean(TpServersDao.class);
        if (type == 1) {
            //压缩包存放的路径
            String zipFilePathV1= FileUploadUtil.getCoursewareZipV1(lessonId);
            //被压缩的文件路径
            String beZipFilePathV1= FileUploadUtil.getCoursewareRealPath(lessonId, (byte) 6);
            //打成压缩包
            SendFileUtil.packCoursewareFile(zipFilePathV1, beZipFilePathV1);

        }
        if (StringUtils.isNotEmpty(filePath)) {
            //发送文件
            List<Thread> findThread = SendFileUtil.findThread(String.valueOf(lessonId) + "_" + String.valueOf(type));
            if (findThread != null) {
                fileTransferRecordDao.updateCancelSendFile(lessonId);
                for (Thread thread : findThread) {
                    thread.interrupt();
                }
            }
            List<Map<String, Object>> serverList = serverDao.getServerList();
            for (Map<String, Object> map : serverList) {
                int serverId = (Integer) map.get("id");
                String serverIp = (String) map.get("serverAddr");
                SendFileThread fileThread = new SendFileThread(serverId, serverIp, lessonId, filePath, type, oaId);
                fileThread.setName(String.valueOf(lessonId) + "_" + String.valueOf(type));
                fileThread.start();
            }
        }
    }

	/**
	 * 打包文件
	 * @param lessonId
	 * @return
	 */
    /**
     * 打成zip包
     * @param zipFilePath 打成压缩包存放的路径
     * @param filePath 被压缩的文件路径
     * @return
     */
	public static String packCoursewareFile(String zipFilePath,String filePath) {
		File zipFile = new File(zipFilePath);
		File file = new File(filePath);
		if(file.exists() && file.isDirectory()) {
			FileToZip fileToZip = new FileToZip();
			fileToZip.zip(zipFile, filePath);
			return zipFile.getPath();
		}
		return null;
	}
	
	
	/** 
     * 将文件流发送至另外服务器的方法（这里有个fileName 
	 * @param serverIp 
     * @param bytes 
     * @param fileName 
	 * @return 
     * @return 从服务器端 响应的流 可通过 new String(bytes); 转换 
     */  
    public static int httpPost(String serverIp, byte[] bytes, String fileName, Integer lessonId, byte type) {  
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
            conn.addRequestProperty("type", String.valueOf(type));
            conn.addRequestProperty("lessonId", lessonId.toString());
            conn.addRequestProperty("fileName", fileName);
            conn.connect();  
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());  
            out.write(bytes);  
            // 刷新、关闭  
            out.flush(); 
            out.close();  
            return conn.getResponseCode();
        } catch (Exception e) { 
        	e.printStackTrace();
        	System.out.println("+++++++++++++ 发送文件到  "+ StringUtils.substring(serverIp, 0, StringUtils.indexOf(serverIp, ":")) +" 失败  +++++++++++++");
        	return 400;  
        }  
    } 
  
    /** 
     * 将文件转换成byte[] 
     *  
     * @param filePath 
     * @return 
     */  
    public static byte[] getBytes(String filePath) {  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
	
    /**
     * 根据进程name查询进程
     * @param lessonId
     * @return
     */
    public static List<Thread> findThread(String lessonId) {
    	List<Thread> list = new ArrayList<Thread>();
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group != null) {
            Thread[] threads = new Thread[(int)(group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for(int i = 0; i < count; i++) {
                if(lessonId == threads[i].getName()) {
                	list.add(threads[i]);
                }
            }
            group = group.getParent();
        }
        return list;
    }

    /**
     * 课件复制
     * @param currLessonId
     * @param targetLessonId
     * @param type
     */
	public static void copy(int currLessonId, int targetLessonId, byte type) {
		String path = FileUploadUtil.getUploadHomePath();
		String typePath = "";
		switch (type) {
		case 1:  //课件文件复制
			typePath = FILE_BASE_PATH;
			break;
		default:  //视频复制
			typePath = VIDEO_PATH;
			break;
		}
		//课件文件复制
		String currCoursewareFilePath = path + typePath + currLessonId;
		File currCoursewareFile = new File(currCoursewareFilePath);
		if(currCoursewareFile.exists() && currCoursewareFile.isDirectory()) {
			String targetCoursewareFilePath = path + typePath + targetLessonId;
			FileToZip.copy(currCoursewareFile.getPath(), targetCoursewareFilePath);
		}
		
	}

}
