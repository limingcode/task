package com.skyedu.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OrderBy;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * WaWork entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_Work")
public class WaWork implements java.io.Serializable {

	// Fields

	private Integer ID;
	//题类
	private WaCategory waCategory;
	//课次
	private WaLesson waLesson;
	private Timestamp createDate;
	private Timestamp modifyDate;
	//作业名称
	private String workName;
	//创建人oaid
	private Integer oaId;
	//作业总时间
	private Integer standardTime;
	//作业总分
	private Double score;
	//答题卡
	private Set<WaResultCard> waResultCards = new HashSet<WaResultCard>(0);
	//作业题
	private Set<WaWorkInfo> waWorkInfos = new HashSet<WaWorkInfo>(0);

	// Constructors

	/** default constructor */
	public WaWork() {
	}

	/** minimal constructor */
	public WaWork(WaCategory waCategory, WaLesson waLesson,
			Timestamp createDate, Timestamp modifyDate, String workName,
			Integer oaId, Integer standardTime, Double score) {
		this.waCategory = waCategory;
		this.waLesson = waLesson;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.workName = workName;
		this.oaId = oaId;
		this.standardTime = standardTime;
		this.score = score;
	}

	/** full constructor */
	public WaWork(WaCategory waCategory, WaLesson waLesson,
			Timestamp createDate, Timestamp modifyDate, String workName,
			Integer oaId, Integer standardTime, Double score,
			Set<WaResultCard> waResultCards, Set<WaWorkInfo> waWorkInfos) {
		this.waCategory = waCategory;
		this.waLesson = waLesson;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.workName = workName;
		this.oaId = oaId;
		this.standardTime = standardTime;
		this.score = score;
		this.waResultCards = waResultCards;
		this.waWorkInfos = waWorkInfos;
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
	@JoinColumn(name = "category", nullable = false)
	public WaCategory getWaCategory() {
		return this.waCategory;
	}

	public void setWaCategory(WaCategory waCategory) {
		this.waCategory = waCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson", nullable = false)
	public WaLesson getWaLesson() {
		return this.waLesson;
	}

	public void setWaLesson(WaLesson waLesson) {
		this.waLesson = waLesson;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waWork")
	public Set<WaResultCard> getWaResultCards() {
		return this.waResultCards;
	}

	public void setWaResultCards(Set<WaResultCard> waResultCards) {
		this.waResultCards = waResultCards;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waWork")
	@OrderBy("ID")
	public Set<WaWorkInfo> getWaWorkInfos() {
		return this.waWorkInfos;
	}

	public void setWaWorkInfos(Set<WaWorkInfo> waWorkInfos) {
		this.waWorkInfos = waWorkInfos;
	}

}