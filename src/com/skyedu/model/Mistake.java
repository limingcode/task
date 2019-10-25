package com.skyedu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "WA_Mistakes")
public class Mistake {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iD;
	// 作业题目
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workInfo", nullable = false)
	private WorkInfo workInfo;
	// 所属答题卡
	@Column(name = "resultCard")
	private Integer resultCard;
	// 创建时间
	@Column(name = "createDate")
	private Date createDate;
	// 学生id
	@Column(name = "studentId")
	private Integer studentId;


	public WorkInfo getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(WorkInfo workInfo) {
		this.workInfo = workInfo;
	}

	public Integer getResultCard() {
		return resultCard;
	}

	public void setResultCard(Integer resultCard) {
		this.resultCard = resultCard;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getiD() {
		return iD;
	}

	public void setiD(Integer iD) {
		this.iD = iD;
	}
}
