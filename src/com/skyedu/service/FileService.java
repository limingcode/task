package com.skyedu.service;

/**
 * 文件操作Service
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-10-18
 */
public abstract interface FileService {

	/**
	 * 
	 * 下载文件保存到本地
	 * 
	 * @param httpurl
	 *            文件网络地址
	 * @return 本地文件地址
	 */
	public String download(FileType fileType, String httpurl);
	
	public enum FileType {
		image;
	}
	
}
