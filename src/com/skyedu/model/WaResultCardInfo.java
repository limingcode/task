package com.skyedu.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * WaResultCardInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_ResultCardInfo")
public class WaResultCardInfo implements java.io.Serializable {

	// Fields

	private Integer ID;
	//作业题目
	private WaWorkInfo waWorkInfo;
	//答题卡
	private WaResultCard waResultCard;
	private Timestamp createDate;
	private Timestamp modifyDate;
	//学生作答使用时间
	private Integer useTime;
	//学生作答答案
	private String anwser;

	// Constructors

	/** default constructor */
	public WaResultCardInfo() {
	}

	/** minimal constructor */
	public WaResultCardInfo(WaWorkInfo waWorkInfo, WaResultCard waResultCard,
			Timestamp createDate, Timestamp modifyDate) {
		this.waWorkInfo = waWorkInfo;
		this.waResultCard = waResultCard;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	/** full constructor */
	public WaResultCardInfo(WaWorkInfo waWorkInfo, WaResultCard waResultCard,
			Timestamp createDate, Timestamp modifyDate, Integer useTime,
			String anwser) {
		this.waWorkInfo = waWorkInfo;
		this.waResultCard = waResultCard;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.useTime = useTime;
		this.anwser = anwser;
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
	@JoinColumn(name = "workInfo", nullable = false)
	public WaWorkInfo getWaWorkInfo() {
		return this.waWorkInfo;
	}

	public void setWaWorkInfo(WaWorkInfo waWorkInfo) {
		this.waWorkInfo = waWorkInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resultCard", nullable = false)
	public WaResultCard getWaResultCard() {
		return this.waResultCard;
	}

	public void setWaResultCard(WaResultCard waResultCard) {
		this.waResultCard = waResultCard;
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

	@Column(name = "useTime")
	public Integer getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	@Column(name = "anwser", length = 1000)
	public String getAnwser() {
		return this.anwser;
	}

	public void setAnwser(String anwser) {
		this.anwser = anwser;
	}

}