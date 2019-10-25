package com.skyedu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "WA_Work")
public class Work {

	private Integer iD;
	//题类
	private Category category;
	//课次
	private LessonBean lesson;
	private Date createDate;
	private Date modifyDate;
	//作业名称
	private String workName;
	//创建人oaid
	private Integer oaId;
	//作业总时间
	private Integer standardTime;
	//作业总分
	private Double score;
	//zip文件大小
	private Long sourceSize;
	//打包时间，与修改时间不一致则需要重新打包
	private Date zipDate;
	//打包状态
	private Integer zipState;
	//zip包下载地址
	private String downUrl;
	//作业题
	private List<WorkInfo> workInfoList;
//	private List<ResultCard> resultCardList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson", nullable = false)
	public LessonBean getLesson() {
		return this.lesson;
	}

	public void setLesson(LessonBean lesson) {
		this.lesson = lesson;
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

	@Column(name = "workName", nullable = false, length = 200)
	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	@Column(name = "oaId", nullable = false)
	public Integer getOaId() {
		return this.oaId;
	}

	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}

	@Column(name = "standardTime", nullable = false)
	public Integer getStandardTime() {
		return this.standardTime;
	}

	public void setStandardTime(Integer standardTime) {
		this.standardTime = standardTime;
	}

	@Column(name = "score", nullable = false, precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "work")
	@OrderBy("iD")
	public List<WorkInfo> getWorkInfoList() {
		return workInfoList;
	}

	public void setWorkInfoList(List<WorkInfo> workInfoList) {
		this.workInfoList = workInfoList;
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
	
	@Column(name = "sourceSize", nullable = true)
	public Long getSourceSize() {
		return sourceSize;
	}

	public void setSourceSize(Long sourceSize) {
		this.sourceSize = sourceSize;
	}

	@Column(name = "zipDate", nullable = true)
	public Date getZipDate() {
		return zipDate;
	}

	public void setZipDate(Date zipDate) {
		this.zipDate = zipDate;
	}

	@Column(name = "zipState", nullable = true)
	public Integer getZipState() {
		return zipState;
	}

	public void setZipState(Integer zipState) {
		this.zipState = zipState;
	}

	@Column(name = "downUrl", nullable = true)
	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

/*	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "work")
	@OrderBy("iD")
	public List<ResultCard> getResultCardList() {
		return resultCardList;
	}*/

//	public void setResultCardList(List<ResultCard> resultCardList) {
//		this.resultCardList = resultCardList;
//	}

}
