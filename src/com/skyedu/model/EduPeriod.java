package com.skyedu.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 学期表
 */
@Entity
@Table(name = "Edu_Period")
public class EduPeriod implements java.io.Serializable {

	// Fields

	private Integer id;//学期id
	private String year;//学期年
	private String name;//学期名称
	private Date beginDate;//开始日期
	private Date endDate;//截止日期
	private Integer state;
	private Integer status;
	private String termName;//综合名称

	// Constructors

	/** default constructor */
	public EduPeriod() {
	}

	/** full constructor */
	public EduPeriod(String year, String name, Date beginDate, Date endDate,
			Integer state, Integer status, String termName) {
		this.year = year;
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.state = state;
		this.status = status;
		this.termName = termName;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "year", length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "beginDate", length = 10)
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "endDate", length = 10)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "termName", length = 20)
	public String getTermName() {
		return this.termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

}