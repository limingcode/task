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
 * WaHierarchy entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_Hierarchy")
public class WaHierarchy implements java.io.Serializable {

	// Fields

	private Integer ID;
	private Timestamp createDate;
	//年级id
	private String grade;
	//学科id
	private String subject;
	//层次id
	private String cate;
	//课次
	private Set<WaLesson> waLessons = new HashSet<WaLesson>(0);
	/*//题目
	private Set<WaQuestion> waQuestions = new HashSet<WaQuestion>(0);*/

	// Constructors

	/** default constructor */
	public WaHierarchy() {
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

	@Column(name = "grade", nullable = false)
	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "subject", nullable = false)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "cate", nullable = false)
	public String getCate() {
		return cate;
	}


	public void setCate(String cate) {
		this.cate = cate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waHierarchy")
	public Set<WaLesson> getWaLessons() {
		return this.waLessons;
	}

	public void setWaLessons(Set<WaLesson> waLessons) {
		this.waLessons = waLessons;
	}

	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waHierarchy")
	public Set<WaQuestion> getWaQuestions() {
		return this.waQuestions;
	}

	public void setWaQuestions(Set<WaQuestion> waQuestions) {
		this.waQuestions = waQuestions;
	}*/

}