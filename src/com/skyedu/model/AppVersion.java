package com.skyedu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "WA_AppVersion")
public class AppVersion {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iD;
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "version")
	private String version;
	@Column(name = "path")
	private String path;
	@OneToMany(targetEntity = AppContent.class, cascade = { CascadeType.ALL }, mappedBy = "appVersion")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("iD")
	private List<AppContent> appContentList = new ArrayList<AppContent>();

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<AppContent> getAppContentList() {
		return appContentList;
	}

	public void setAppContentList(List<AppContent> appContentList) {
		this.appContentList = appContentList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
