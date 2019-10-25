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
 * WaMistakes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_Mistakes")
public class WaMistakes implements java.io.Serializable {

	// Fields

	private Integer ID;
	//作业题目
	private WaWorkInfo waWorkInfo;
	//所属答题卡
	private WaResultCard waResultCard;
	private Timestamp createDate;
	//学生id
	private Integer studentId;

	// Constructors

	/** default constructor */
	public WaMistakes() {
	}

	/** full constructor */
	public WaMistakes(WaWorkInfo waWorkInfo, WaResultCard waResultCard,
			Timestamp createDate, Integer studentId) {
		this.waWorkInfo = waWorkInfo;
		this.waResultCard = waResultCard;
		this.createDate = createDate;
		this.studentId = studentId;
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

	@Column(name = "studentId", nullable = false)
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

}