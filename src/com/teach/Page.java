package com.teach;

import java.util.List;
import java.util.Map;

/**
 * 分页查询工具类
 * @author skyedu_beyond
 *
 */
public class Page {

	private boolean status;
	
	private String message;
	
	private int currPage;
	
	private int pageSize;
	
	private int totalPage;
	
	private boolean hasPrePage;
	
	private boolean hasNextPage;
	
	private List<Map<String, Object>> dataList;
	
	private int maxLesson;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isHasPrePage() {
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}
	
	public int getMaxLesson() {
		return maxLesson;
	}
	
	public void setMaxLesson(int maxLesson) {
		this.maxLesson = maxLesson;
	}
	
}
