package com.skyedu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 年级表
 */
@Entity
@Table(name = "Edu_Grade")
public class EduGrade implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;//年级编号
	private String name;//年级名称

	// Constructors

	/** default constructor */
	public EduGrade() {
	}

	/** full constructor */
	public EduGrade(String code, String name) {
		this.code = code;
		this.name = name;
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

	@Column(name = "code", length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}