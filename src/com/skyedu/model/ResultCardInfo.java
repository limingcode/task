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
@Table(name = "WA_ResultCardInfo")
public class ResultCardInfo {

	private Integer iD;
	//作业题目
	private WorkInfo workInfo;
	//答题卡
	private ResultCard resultCard;
	private Date createDate;
	private Date modifyDate;
	//学生作答使用时间
	private Integer useTime;
	//学生作答答案
	private String answer;
	//学生作答状态，对错需注意
	private Integer state;
	//作答得分
	private Double score;

	

	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "workInfo", nullable = false)
	public WorkInfo getWorkInfo() {
		return this.workInfo;
	}

	public void setWorkInfo(WorkInfo workInfo) {
		this.workInfo = workInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resultCard", nullable = false)
	public ResultCard getResultCard() {
		return this.resultCard;
	}

	public void setResultCard(ResultCard resultCard) {
		this.resultCard = resultCard;
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

	@Column(name = "useTime")
	public Integer getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	@Column(name = "answer", length = 1000)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "score", nullable = true)
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
