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
 * WaLesson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_Lesson")
public class WaLesson implements java.io.Serializable {

	// Fields

	private Integer ID;
	//层次
	private WaHierarchy waHierarchy;
	//排序字段
	private Integer sortNo;
	//学期
	private Integer period;
	//开放时间
	private Timestamp openTime;
	//作业
	private Set<WaWork> waWorks = new HashSet<WaWork>(0);

	// Constructors

	/** default constructor */
	public WaLesson() {
	}

	/** minimal constructor */
	public WaLesson(WaHierarchy waHierarchy, Integer sortNo, Integer period,
			Timestamp openTime) {
		this.waHierarchy = waHierarchy;
		this.sortNo = sortNo;
		this.period = period;
		this.openTime = openTime;
	}

	/** full constructor */
	public WaLesson(WaHierarchy waHierarchy, Integer sortNo, Integer period,
			Timestamp openTime, Set<WaWork> waWorks) {
		this.waHierarchy = waHierarchy;
		this.sortNo = sortNo;
		this.period = period;
		this.openTime = openTime;
		this.waWorks = waWorks;
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
	@JoinColumn(name = "hierarchy", nullable = false)
	public WaHierarchy getWaHierarchy() {
		return this.waHierarchy;
	}

	public void setWaHierarchy(WaHierarchy waHierarchy) {
		this.waHierarchy = waHierarchy;
	}

	@Column(name = "sortNo", nullable = false)
	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	
	@Column(name = "period", nullable = false)
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Column(name = "openTime", nullable = false, length = 23)
	public Timestamp getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Timestamp openTime) {
		this.openTime = openTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waLesson")
	public Set<WaWork> getWaWorks() {
		return this.waWorks;
	}

	public void setWaWorks(Set<WaWork> waWorks) {
		this.waWorks = waWorks;
	}

}