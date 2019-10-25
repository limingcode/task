package com.image.tag.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.image.tag.Message;

public class ImageTagUtils {
	
	public static String getHomePath() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return System.getProperty("user.dir") + File.separator + "webapps"; 
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession().getServletContext().getRealPath("") + File.separator + "..";  
	}

    /**
     * 获取文件的zip
     * @param bookId
     * @param period
     * @param bookName
     * @return
     */
	public static String getZipFile(String bookId, String period, String bookName) {
		return getHomePath() + File.separator + "upload" + File.separator + "imageTag" + 
				File.separator + "zip" + File.separator + period  + File.separator + bookId + File.separator + bookName + ".zip";
	}

    /**
     *  解压zip路径
     * @param bookId
     * @param period
     * @param bookName
     * @return
     */
    public static String getUnZipFile(String bookId, String period,String bookName) {
        return getHomePath() + File.separator + "upload" + File.separator + "imageTag" +
                File.separator + "file" + File.separator + period  + File.separator + bookId + File.separator+bookName+File.separator+"formal"+File.separator;
    }
	
	/**
	 * 得到json文件
	 * @param grade
	 * @param subject
	 * @param cate
	 * @param lessonId
	 * @return
	 */
	public static String getJsonFile(int bookId, String period, String bookName) {
		return getBookPath(bookId, period, bookName, true) + bookId+ "_" + bookName +".json";
	}
	
	/**
	 * 得到书箱根目录
	 * @param lessonId
	 * @return
	 */
	public static String getBookPath(int bookId, String period, String bookName, boolean isFormal) {
        String path = File.separator + "upload" + File.separator + "imageTag" + File.separator + "file" + File.separator + period +
                File.separator + bookId + File.separator + bookName + File.separator;
        if (isFormal)
            return path + "formal" + File.separator;
        return path + "temp" + File.separator;
    }
    public static String getBookJson(int bookId,String bookName) {
        return  getHomePath()+File.separator + "AppTask" + File.separator + "bookJson" + File.separator +bookId + "_" + bookName + ".json" ;
    }
    public static String getBookJson(int bookId,String bookName,String tag) {
        return  getHomePath()+File.separator + "AppTask" + File.separator + "bookJson" + File.separator +tag + File.separator +bookId + File.separator +bookId + "_" + bookName + ".json" ;
    }
    /**
	 * 得到图片标注文件上传目录
	 * @param lessonId
	 * @return
	 */
	public static String getImageTagFileUploadPath(int bookId, String period, String bookName, int type, boolean isFormal) {
		if (type == 1) {
			return getBookPath(bookId, period, bookName, isFormal) + "imageFile" + File.separator;
		}
		return getBookPath(bookId, period, bookName, isFormal) + "audioFile" + File.separator;
	}
	
	/**
	 * 生成上传文件名
	 * @param fileName
	 * @param pageNum
	 * @param index
	 * @return
	 */
	public static String getImageUploadName(String fileName, int index) {
		return "image_"+ index + "_" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
	}
	

    /**
     * 生成上传文件名
     * @param fileName
     * @param bookId
     * @return
     */
	public static String getAudioUploadName(String fileName,int bookId) {
		return "audio_"+bookId+"_"+ System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * 文件格式是否支持上传
	 * @param fileName
	 * @param fileType
	 * @return
	 */
	public static Message getIsSupportFileType(String fileName, byte fileType) {
		if (1 == fileType || 7 == fileType) { //图片上传
			if(!isImageType(fileName)) {
				return new Message(false, "该图片格式不支持上传!");
			}
		} else if (2 == fileType) { //音频上传
			if(!isAudioType(fileName)) {
				return new Message(false, "该音频格式不支持上传!");
			}
		} else if (3 == fileType) {
			if(!isFlushType(fileName)) {
				return new Message(false, "该动画格式不支持上传!");
			}
		} else {
			if(!isVideoType(fileName)) {
				return new Message(false, "该视频格式不支持上传!");
			}
		}
		return new Message(true, null);
	}
	
	/**
	 * 是否为图片格式
	 * @param fileName
	 * @return
	 */
	public static boolean isImageType(String fileName) {
		if( StringUtils.endsWithIgnoreCase(fileName, ".JPG") ||
			StringUtils.endsWithIgnoreCase(fileName, ".JPEG") ||		
			StringUtils.endsWithIgnoreCase(fileName, ".BMP") ||
			StringUtils.endsWithIgnoreCase(fileName, ".PNG") ||	
			StringUtils.endsWithIgnoreCase(fileName, ".GIF")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否为音频格式
	 * @param fileName
	 * @return
	 */
	public static boolean isAudioType(String fileName) {
//		if( StringUtils.endsWithIgnoreCase(fileName, ".MP3") ||
//		  	StringUtils.endsWithIgnoreCase(fileName, ".WMA") ||
//		  	StringUtils.endsWithIgnoreCase(fileName, ".WAVE")) {
//			return true;
//		}
		if( StringUtils.endsWithIgnoreCase(fileName, ".MP3")) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 是否为flush格式
	 * @param fileName
	 * @return
	 */
	public static boolean isFlushType(String fileName) {
		if(StringUtils.endsWithIgnoreCase(fileName, ".SWF")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为视频格式
	 * @param fileName
	 */
	public static Boolean isVideoType(String fileName) {
		if( StringUtils.endsWithIgnoreCase(fileName, ".MP4") ||
			StringUtils.endsWithIgnoreCase(fileName, ".MKV") ||
			StringUtils.endsWithIgnoreCase(fileName, ".AVI")) {
			return true;
		}
		return false;
	}
	
	/**
     * 生成.json格式文件
     */
    public static boolean createJsonFile(String jsonString, String filePath, String fileName) {
        // 标记文件生成是否成功
        boolean flag = true;

        // 拼接文件完整路径
        String fullPath = filePath + File.separator + fileName + ".json";

        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
    
}
