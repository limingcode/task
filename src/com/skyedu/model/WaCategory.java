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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * WaCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_Category")
public class WaCategory implements java.io.Serializable {

	// Fields

	private Integer ID;
	private Timestamp createDate;
	//题类名称
	private String categoryName;
	//该题类的作业
	private Set<WaWork> waWorks = new HashSet<WaWork>(0);

	// Constructors

	/** default constructor */
	public WaCategory() {
	}

	/** minimal constructor */
	public WaCategory(Timestamp createDate, String categoryName) {
		this.createDate = createDate;
		this.categoryName = categoryName;
	}

	/** full constructor */
	public WaCategory(Timestamp createDate, String categoryName,
			Set<WaWork> waWorks) {
		this.createDate = createDate;
		this.categoryName = categoryName;
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

	@Column(name = "createDate", nullable = false, length = 23)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "categoryName", nullable = false, length = 50)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waCategory")
	public Set<WaWork> getWaWorks() {
		return this.waWorks;
	}

	public void setWaWorks(Set<WaWork> waWorks) {
		this.waWorks = waWorks;
	}

}