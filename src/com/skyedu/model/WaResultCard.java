package com.skyedu.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * WaResultCard entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_ResultCard")
public class WaResultCard implements java.io.Serializable {

	// Fields

	private Integer ID;
	//所属作业
	private WaWork waWork;
	private Timestamp createDate;
	private Timestamp modifyDate;
	//开放时间
	private Timestamp openTime;
	//学生id
	private Integer studentId;
	//学生作答总时间
	private Integer totalTime;
	//总得分
	private Double score;
	//是否做过
	private Integer hasDealed;
	//作业详细
	private Set<WaResultCardInfo> waResultCardInfos = new HashSet<WaResultCardInfo>(
			0);
	//错题集
	private Set<WaMistakes> waMistakeses = new HashSet<WaMistakes>(0);

	// Constructors

	/** default constructor */
	public WaResultCard() {
	}

	/** minimal constructor */
	public WaResultCard(WaWork waWork, Timestamp createDate,
			Timestamp modifyDate, Timestamp openTime, Integer studentId,
			Integer totalTime, Double score, Integer hasDealed) {
		this.waWork = waWork;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.openTime = openTime;
		this.studentId = studentId;
		this.totalTime = totalTime;
		this.score = score;
		this.hasDealed = hasDealed;
	}

	/** full constructor */
	public WaResultCard(WaWork waWork, Timestamp createDate,
			Timestamp modifyDate, Timestamp openTime, Integer studentId,
			Integer totalTime, Double score, Integer hasDealed,
			Set<WaResultCardInfo> waResultCardInfos,
			Set<WaMistakes> waMistakeses) {
		this.waWork = waWork;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.openTime = openTime;
		this.studentId = studentId;
		this.totalTime = totalTime;
		this.score = score;
		this.hasDealed = hasDealed;
		this.waResultCardInfos = waResultCardInfos;
		this.waMistakeses = waMistakeses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer id) {
		this.ID = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work", nullable = false)
	public WaWork getWaWork() {
		return this.waWork;
	}

	public void setWaWork(WaWork waWork) {
		this.waWork = waWork;
	}

	@Column(name = "createDate", nullable = false, length = 23)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "modifyDate", nullable = false, length = 23)
	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "openTime", nullable = false, length = 23)
	public Timestamp getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Timestamp openTime) {
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waResultCard")
	public Set<WaResultCardInfo> getWaResultCardInfos() {
		return this.waResultCardInfos;
	}

	public void setWaResultCardInfos(Set<WaResultCardInfo> waResultCardInfos) {
		this.waResultCardInfos = waResultCardInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waResultCard")
	public Set<WaMistakes> getWaMistakeses() {
		return this.waMistakeses;
	}

	public void setWaMistakeses(Set<WaMistakes> waMistakeses) {
		this.waMistakeses = waMistakeses;
	}

}