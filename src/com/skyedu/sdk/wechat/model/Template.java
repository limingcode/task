package com.skyedu.sdk.wechat.model;

import java.util.Map;

/**
 * 模板消息类
 * 
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-11-9
 */
public class Template {
	private String template_id;
	private String touser;
	private String url;
	private Map<String, TemplateData> data;

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, TemplateData> getData() {
		return data;
	}

	public void setData(Map<String, TemplateData> data) {
		this.data = data;
	}

}
