package com.teach.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUploadUtil {
	
	/**
	 * ppt文件上传地址-图片（1）
	 */
	private static final String IMAGE_FILE_UPLOAD_PATH = "/upload/teach/courseware/lessonId/type1/image/";
	/**
	 * ppt文件上传地址-音频（2）
	 */
	private static final String AUDIO_FILE_UPLOAD_PATH = "/upload/teach/courseware/lessonId/type1/audio/";
	/**
	 * ppt文件上传地址-动画（3）
	 */
	private static final String FLUSH_FILE_UPLOAD_PATH = "/upload/teach/courseware/lessonId/type1/flush/";
	/**
	 * ppt文件上传地址-视频（4）
	 */
	private static final String VIDEO_FILE_UPLOAD_PATH = "/upload/teach/courseware/lessonId/type1/video/";
	/**
	 * ppt文件上传地址-主题（7）
	 */
	private static final String THEME_FILE_UPLOAD_PATH = "/upload/teach/courseware/lessonId/type1/theme/";
	/**
	 * 说课视频（5）上传地址
	 */
	private static final String LESSON_VIDEO_UPLOAD_PATH = "/upload/teach/video/lessonId/lesson/";
	/**
	 * 说课视频（6）实录地址
	 */
	private static final String MEMOIR_VIDEO_UPLOAD_PATH = "/upload/teach/video/lessonId/memoir/";
	/**
	 * 上传PPt所需要的文件
	 */
	public static final String UPLOAD_COURSEWARE_FILE = "/sys/teach/staticFile/";

	
	public static final String FILE_UPLOAD_PATH_REGULAR_EXPRESSION = "/upload/teach/courseware/\\d{0,10}/type1/(video|audio|image|flush)/(\\w|-)+\\.(JPG|JPEG|BMP|PNG|GIF|MP3|WMA|WAVE|SWF|MP4|MKV|AVI)\\b";

	/**
	 * 对上传名字进行处理：fileName + System.currentTimeMillis + 文件后缀名，防止上传重名出现
	 * @param fileName
	 * @return
	 */
	public static String getUploadName(byte fileType, String fileName) {
		if(StringUtils.isEmpty(fileName))
//			return fileName;
			return "image" + System.currentTimeMillis() + ".png";  //直接粘贴上传文件无fileName，直接生成文件名
		switch (fileType) {
		case 1:
			fileName = "image" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
			break;
		case 2:
			fileName = "audio" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
			break;
		case 3:
			fileName = "flash" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
			break;
		case 4:
			fileName = "video" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
			break;
		case 5:
			fileName = "lessonVideo" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
			break;
		case 6:
			fileName = "memoirVideo" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
			break;
		case 7:
			fileName = "theme" + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
			break;
		default:
			break;
		}
		return fileName;
	}
	
	/**
	 * 获取上传文件类型
	 * @param fileName
	 * @return 0--文件名为空，1--图片， 2--音频， 3--flush动画， 4--视频， 9--不支持上传格式
	 */
	public static Byte getUploadType(String fileName) {
		if(StringUtils.isEmpty(fileName))
			return 0;
		if(isImageType(fileName)) {  //图片
			return 1;
		} else if(isAudioType(fileName)) { //音频
			return 2;
		} else if(	StringUtils.endsWithIgnoreCase(fileName, ".SWF")) { //flush动画
			return 3;
		} else if(isVideoType(fileName)) { //视频
			return 4;
		}
		return 9;
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
		if( StringUtils.endsWithIgnoreCase(fileName, ".MP3") ||
		  	StringUtils.endsWithIgnoreCase(fileName, ".WMA") ||
		  	StringUtils.endsWithIgnoreCase(fileName, ".WAVE")) {
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
	 * 得到上传目录 
	 * @param lessonId 
	 * @param fileName
	 * @return
	 */
	public static String getUploadPath(byte type, int lessonId) {
		switch (type) {
		case 1:
			return IMAGE_FILE_UPLOAD_PATH.replace("lessonId", String.valueOf(lessonId));
		case 2:
			return AUDIO_FILE_UPLOAD_PATH.replace("lessonId", String.valueOf(lessonId));
		case 3:
			return FLUSH_FILE_UPLOAD_PATH.replace("lessonId", String.valueOf(lessonId));
		case 4:
			return VIDEO_FILE_UPLOAD_PATH.replace("lessonId", String.valueOf(lessonId));
		case 5:
			return LESSON_VIDEO_UPLOAD_PATH.replace("lessonId", String.valueOf(lessonId));
		case 6:
			return MEMOIR_VIDEO_UPLOAD_PATH.replace("lessonId", String.valueOf(lessonId));
		case 7:
			return THEME_FILE_UPLOAD_PATH.replace("lessonId", String.valueOf(lessonId));
		default:
			return null;
		}
	}
	
	public static String getHomePath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession().getServletContext().getRealPath("");
	}
	
	public static String getUploadHomePath() {
		return getHomePath() + "/..";
	}
	
	/**
	 * 得到真实上传路径
	 * @param type
	 * @param lessonId
	 * @return
	 */
	public static String getRealUploadPath(byte type, int lessonId) {
		return getUploadHomePath() + getUploadPath(type,lessonId);
	}
	
	/**
	 * 得到课件路径
	 * @param lessonId
	 * @return
	 */
	public static String getCoursewarePath(int lessonId, byte type) {
        switch (type) {
            case 1:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/";
            case 3:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1"+"/type2/type2/";
            case 4:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1";
            case 5:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1/"+"/type2/";
            case 6:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1/skin";
            case 7:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1/skin/pptData";
            case 8:
                return SendFileUtil.FILE_BASE_PATH + lessonId ;
            case 9:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1"+"/type3/type2/";
            case 10:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1"+"/type3/";
            default:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/type" + type + "/";
           /* default:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/type"+ type +"/";*/
        }
    }
    @Deprecated
    public static String getCoursewarePathZip(int lessonId, byte type) {
        switch (type) {
            case 1:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/";
            case 3:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/V1"+"/type2/type2/";
            case 4:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/V1";
            case 5:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/V1/"+"/type2/";
            case 6:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/V1/skin";
            case 7:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/V1/skin/pptData";
            case 8:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId ;
            case 9:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/V1"+"/type3/type2/";
            case 10:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/V1"+"/type3/";
            default:
                return SendFileUtil.FILE_BASE_PATH_zip + lessonId + "/type" + type + "/";
           /* default:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/type"+ type +"/";*/
        }
    }
    public static String getCoursewarePath(int lessonId, byte type,boolean flag) {
        switch (type) {
            case 1:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/";
            case 3:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1/type2/";
            case 4:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/V1/";
            default:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/type" + type +"/type" + type + "/";
           /* default:
                return SendFileUtil.FILE_BASE_PATH + lessonId + "/type"+ type +"/";*/
        }
    }
	/**
	 * 得到课件真实路径
	 * @param type
	 * @param lessonId
	 * @return
	 */
	public static String getCoursewareRealPath(int lessonId, byte type) {
        return getUploadHomePath() + getCoursewarePath(lessonId, type);
    }
    @Deprecated
    public static String getCoursewareRealPathZIP(int lessonId, byte type) {
        return getUploadHomePath() + getCoursewarePathZip(lessonId, type);
    }
  /*  public static String getCoursewareRealPath(int lessonId, byte type,boolean falg) {
        return getUploadHomePath() + getCoursewarePath(lessonId, type,falg);
    }*/

    /**
     *  得到课件zip真实路径
     * @param lessonId
     * @return
     */
	public static String getCoursewareZipPath(int lessonId) {
		return getUploadHomePath() + SendFileUtil.ZIP_PATH + lessonId + "/";
	}
	
	/**
	 * 得到压缩包
	 * @param lessonId
	 * @return
	 */
	public static String getCoursewareZip(int lessonId) {
		return getCoursewareZipPath(lessonId) + lessonId + ".zip";
	}

    public static String getCoursewareZipV1(int lessonId) {
        return getCoursewareZipPath(lessonId) + lessonId+"_V1" + ".zip";
    }



    /**
     * 上传PPt所需要的文件路径
     * @param isV8
     * @return
     */
	public static String getUploadCoursewareFileDir(boolean isV8) {
		if (isV8) {//ISpring_V8
			return getHomePath() + UPLOAD_COURSEWARE_FILE + "ISpring_V8";
		}//ISpring_V7
		return getHomePath() + UPLOAD_COURSEWARE_FILE + "ISpring_V7";
	}
    public static String getUploadCoursewareFileDir(int type) {
        return getHomePath() + UPLOAD_COURSEWARE_FILE + "ISpring_V9";
    }
    /**
     * 获取js 等文件
     * @return
     */
    public static String getCommonFile() {
        return getHomePath() + UPLOAD_COURSEWARE_FILE + "commonFile";
    }
	/**
	 * 删除多个文件
	 * @param urls
	 */
	public static void deleteFile(String... urls) {
		String basePath = getUploadHomePath();
		for (String url : urls) {
			String fileName = basePath + url; 
			File file = new File(fileName);
			if (file.exists() && file.isFile()) {
				file.delete();
			} 
		}
	}
	
	/**
	 * 删除多个文件夹
	 * @param urls
	 */
	public static void deleteDirectory(String... urls) {
		for (String url : urls) {
			File dir = new File(url);
			if(dir.exists()) {
				FileUtils.deleteQuietly(dir);
			}
		}
	}
	
	
	/**
	 * 删除上传的ppt文件
	 * @param lessonId
	 */
//	public static void deleteUploadCoursewareFile(int lessonId) {
//		// TODO Auto-generated method stub
//		String coursewarePath = getCoursewareRealPath(lessonId);
//		String zipPath = getCoursewareZipPath(lessonId);
//		deleteDirectory(coursewarePath, zipPath);
//	}

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
	
	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}
	
}
	
