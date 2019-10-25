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
 * WaWorkInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_WorkInfo")
public class WaWorkInfo implements java.io.Serializable {

	// Fields

	private Integer ID;
	//作业
	private WaWork waWork;
	//题目
	private WaQuestion waQuestion;
	private Timestamp createDate;
	private Timestamp modifyDate;
	//排序字段
	private Integer sortNo;
	//援引此题的答题卡明细
	private Set<WaResultCardInfo> waResultCardInfos = new HashSet<WaResultCardInfo>(
			0);
	//援引此题的错题集
	private Set<WaMistakes> waMistakeses = new HashSet<WaMistakes>(0);

	// Constructors

	/** default constructor */
	public WaWorkInfo() {
	}

	/** minimal constructor */
	public WaWorkInfo(WaWork waWork, WaQuestion waQuestion,
			Timestamp createDate, Timestamp modifyDate, Integer sortNo) {
		this.waWork = waWork;
		this.waQuestion = waQuestion;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.sortNo = sortNo;
	}

	/** full constructor */
	public WaWorkInfo(WaWork waWork, WaQuestion waQuestion,
			Timestamp createDate, Timestamp modifyDate, Integer sortNo,
			Set<WaResultCardInfo> waResultCardInfos,
			Set<WaMistakes> waMistakeses) {
		this.waWork = waWork;
		this.waQuestion = waQuestion;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.sortNo = sortNo;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question", nullable = false)
	public WaQuestion getWaQuestion() {
		return this.waQuestion;
	}

	public void setWaQuestion(WaQuestion waQuestion) {
		this.waQuestion = waQuestion;
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

	@Column(name = "sortNo", nullable = false)
	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waWorkInfo")
	public Set<WaResultCardInfo> getWaResultCardInfos() {
		return this.waResultCardInfos;
	}

	public void setWaResultCardInfos(Set<WaResultCardInfo> waResultCardInfos) {
		this.waResultCardInfos = waResultCardInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waWorkInfo")
	public Set<WaMistakes> getWaMistakeses() {
		return this.waMistakeses;
	}

	public void setWaMistakeses(Set<WaMistakes> waMistakeses) {
		this.waMistakeses = waMistakeses;
	}

}