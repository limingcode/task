package com.teach;

import java.util.List;

/**
 * 文件上传返回信息类
 * @author skyedu_beyond
 *
 */
public class FileUploadMessage {

	private boolean isSuccess;
	
	private Byte fileType;
	
	private String url;
	
	private String message;
	
	private List<String> list;
	
	public FileUploadMessage(){
		
	}

	public FileUploadMessage(boolean isSuccess, String message) {
		super();
		this.isSuccess = isSuccess;
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}
	
	public Byte getFileType() {
		return fileType;
	}
	
	public void setFileType(Byte fileType) {
		this.fileType = fileType;
	}
	
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<String> getList() {
		return list;
	}
	
	public void setList(List<String> list) {
		this.list = list;
	}
}
