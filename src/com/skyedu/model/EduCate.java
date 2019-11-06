package com.skyedu.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 层次表
 */

@Entity
@Table(name = "Edu_Cate")
public class EduCate implements java.io.Serializable {

	// Fields

	private Integer id;//层次id
	private String code;//层次编号
	private String name;//层次名称
	private Timestamp recDate;
	private Integer note;

	// Constructors

	/** default constructor */

	public EduCate() {
	}

	/** full constructor */
	public EduCate(String code, String name, Timestamp recDate, Integer note) {
		this.code = code;
		this.name = name;
		this.recDate = recDate;
		this.note = note;
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

	@Column(name = "recDate", length = 23)
	public Timestamp getRecDate() {
		return this.recDate;
	}

	public void setRecDate(Timestamp recDate) {
		this.recDate = recDate;
	}

	@Column(name = "note")
	public Integer getNote() {
		return this.note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

}