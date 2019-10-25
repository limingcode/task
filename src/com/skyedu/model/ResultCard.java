package com.skyedu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;


@Entity
@Table(name = "WA_ResultCard")
public class ResultCard{

	private Integer iD;
	private Date createDate;
	private Date modifyDate;
	//开放时间
	private Date openTime;
	//学生id
	private Integer studentId;
	//学生作答总时间
	private Integer totalTime;
	//总得分
	private Double score;
	//是否做过
	private Integer hasDealed;
	
	private List<ResultCardInfo> resultCardInfoList;
	
	private Work work;
	//正确率
	private String correct;
	
	

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

	@Column(name = "openTime", nullable = false, length = 23)
	public Date getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@Column(name = "studentId", nullable = false)
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Column(name = "totalTime", nullable = false)
	public Integer getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	@Column(name = "score", nullable = false, precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "hasDealed", nullable = false)
	public Integer getHasDealed() {
		return this.hasDealed;
	}

	public void setHasDealed(Integer hasDealed) {
		this.hasDealed = hasDealed;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resultCard")
	@OrderBy("iD")
	public List<ResultCardInfo> getResultCardInfoList() {
		return this.resultCardInfoList;
	}

	public void setResultCardInfoList(List<ResultCardInfo> resultCardInfoList) {
		this.resultCardInfoList = resultCardInfoList;
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

	@Column(name = "correct", nullable = true)
	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "work", nullable = false)
	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	


}
