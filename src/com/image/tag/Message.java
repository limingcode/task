package com.image.tag;

import java.util.List;
import java.util.Map;

/**
 * 文件上传返回信息类
 * @author skyedu_beyond
 *
 */
public class Message {

	private boolean isSuccess;
	
	private Byte fileType;
	
	private String url;
	
	private String message;
	
	private List<Map<String, Object>> list;
	
	public Message(){
		
	}

	public Message(boolean isSuccess, String message) {
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
	
	public List<Map<String, Object>> getList() {
		return list;
	}
	
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
