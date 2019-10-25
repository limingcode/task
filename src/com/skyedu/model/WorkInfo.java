package com.skyedu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WA_WorkInfo")
public class WorkInfo {

	private Integer iD;
	//作业
	private Work work;
	//题目
	private Question question;
	private Date createDate;
	private Date modifyDate;
	//排序字段
	private Integer sortNo;
	//app排序字段
	private String appSortNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work", nullable = false)
	public Work getWork() {
		return this.work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	/*@Column(name = "work", nullable = false)
	public Integer getWork() {
		return this.work;
	}

	public void setWork(Integer work) {
		this.work = work;
	}*/

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question", nullable = false)
	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Column(name = "createDate", nullable = false, length = 23)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "modifyDate", nullable = false, length = 23)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "sortNo")
	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getiD() {
		return iD;
	}

	public void setiD(Integer iD) {
		this.iD = iD;
	}

	@Column(name = "appSortNo")
	public String getAppSortNo() {
		return appSortNo;
	}

	public void setAppSortNo(String appSortNo) {
		this.appSortNo = appSortNo;
	}

}
