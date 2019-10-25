package com.skyedu.sdk.wechat.model;

/**
 * 
 * @author Jinmingming jin_ming_ming@qq.com
 * @date 2015-11-9
 */
public class TemplateData {
	private String value;
	private String color;

	public TemplateData(){}
	public TemplateData(String value,String color){
		this.value = value;
		this.color=color;
	}
	public TemplateData(String value){
		this.value=value;
		this.color="#173177";
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
