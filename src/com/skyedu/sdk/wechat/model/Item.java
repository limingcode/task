package com.skyedu.sdk.wechat.model;

import com.skyedu.sdk.wechat.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")
public class Item{

	@XStreamAlias("Title")
	@XStreamCDATA
	/** 图文消息标题 */
	private String title;
	
	@XStreamAlias("Description")
	@XStreamCDATA
	/** 图文消息描述 */
	private String description;
	
	@XStreamAlias("PicUrl")
	@XStreamCDATA
	/** 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 */
	private String picUrl;
	
	@XStreamAlias("Url")
	@XStreamCDATA
	/** 点击图文消息跳转链接 */
	private String url;
	
	public Item(String title, String description, String picUrl, String url){
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
