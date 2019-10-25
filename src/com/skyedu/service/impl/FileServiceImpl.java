package com.skyedu.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.skyedu.service.FileService;

/**
 * 文件操作Impl
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-18
 */
@Service
public class FileServiceImpl implements FileService, ServletContextAware {
	
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public String download(FileType fileType, String httpurl) {
		//获得年月
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		String date = calendar.get(Calendar.YEAR) + "" + (month < 10 ? "0" + month : month);
		
		String folderName = null;
		// 后缀名后续需校正，目前只保存jpg文件，参考：conn.getContentType()  httpurl.substring(0,httpurl.lastIndexOf(".")).toLowerCase()
		String suffixName = "jpg";
		if (fileType == FileType.image) {
			folderName = "upload/image/" + date + "/";
			
		}
		// 文件保存位置
		String path = folderName + UUID.randomUUID() + "." + suffixName;
				
		try {
			URL url = new URL(httpurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为5秒
//			conn.setConnectTimeout(5 * 1000);
			// 得到输入流
			InputStream inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);
			// 创建文件夹
			File saveDir = new File(this.servletContext.getRealPath(path));
			if (!saveDir.getParentFile().exists()){
				saveDir.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(this.servletContext.getRealPath(path));
			fos.write(getData);
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	 /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    private static byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }    
  

}
