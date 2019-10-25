package com.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.skyedu.model.AppZip;
import com.skyedu.service.AppZipService;

import sun.misc.BASE64Decoder;
import sun.net.www.protocol.http.HttpURLConnection;

public class FileUtil {
	
	/** 
     * 将文件流发送至另外服务器的方法（这里有个fileName 
     *  
     * @param bytes 
     * @param fileName 
     * @return 从服务器端 响应的流 可通过 new String(bytes); 转换 
     */  
    public byte[] httpPost(byte[] bytes, Integer workId ,Integer lessonId,  Integer state) {  
        try {  
            String url = CommonUtil.APPSOCKETURL;  
            URL console = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) console  
                    .openConnection();  
            conn.setConnectTimeout(30000);  
            conn.setReadTimeout(30000);  
            conn.setUseCaches(false);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setRequestMethod("POST");
            conn.addRequestProperty("workId", workId.toString());
            conn.addRequestProperty("state", state.toString());
            conn.addRequestProperty("lessonId", lessonId.toString());
            conn.connect();  
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());  
            out.write(bytes);  
            // 刷新、关闭  
            out.flush();  
            out.close();  
            InputStream is = conn.getInputStream();  
            if (is != null) {  
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
                byte[] buffer = new byte[1024];  
                int len = 0;  
                while ((len = is.read(buffer)) != -1) {  
                    outStream.write(buffer, 0, len);  
                }  
                is.close();  
                return outStream.toByteArray();  
            }
        } catch (Exception e) {  
        	System.out.println("文件发送失败++++++++++++++++++++++++++");
        	e.printStackTrace(); 
        	ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        	AppZipService appZipService = context.getBean(AppZipService.class);
        	AppZip appZip = appZipService.getAppZip(workId);
        	String message = e.getMessage();
        	if (appZip==null) {
        		appZip = new AppZip();
        		appZip.setCount(0);
        		appZip.setCreateDate(new Date());
        		appZip.setMessage(message);
        		appZip.setModifyDate(new Date());
        		appZip.setState(0);
        		appZip.setWorkId(workId);
        		appZip.setLessonId(lessonId);
        		appZipService.saveAppZip(appZip);
        	} else {
        		appZip.setCount(appZip.getCount()+1);
        		appZip.setMessage(message);
        		appZip.setModifyDate(new Date());
        		appZipService.updateAppZip(appZip);
        	}
        }  
        return null;  
    }  
  
    /** 
     * 将文件转换成byte[] 
     *  
     * @param filePath 
     * @return 
     */  
    public byte[] getBytes(String filePath) {  
        byte[] buffer = null;  
        FileInputStream fis = null ;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);  
        try {  
            File file = new File(filePath); 
            fis = new FileInputStream(file);  
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
            try {
				fis.close();  
				bos.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
        }  
        return buffer;  
    }
    
    /**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author: 
	 * @CreateTime: 
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	*/
	public static boolean generateImage(String imgStr, String uploadPath, String fileName) {
		if (imgStr == null)
			return false;
		String imgStrDecode = imgStr.split(",")[1];
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStrDecode);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			
			File dir = new File(uploadPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			OutputStream out = new FileOutputStream(uploadPath + fileName);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
