package com.skyedu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WA_AppBugLog")
public class AppBugLog {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = IDENTITY)
	private Integer ID;
	@Column(name = "uuid")
	private String uuid;
	@Column(name = "fieldName")
	private String fieldName;
	@Column(name = "fieldValue")
	private String fieldValue;
	@Column(name = "createDate")
	private Date createDate;

	public AppBugLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppBugLog(String uuid, String fieldName, String fieldValue, Date createDate) {
		super();
		this.uuid = uuid;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.createDate = createDate;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
