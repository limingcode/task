package com.skyedu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 校区表
 */
@Entity
@Table(name = "Edu_Depa")
public class EduDepa implements java.io.Serializable {

	// Fields

	private Integer id;
	private String code;//校区编号
	private String name;//校区名称
	private String address;//校区地址
	private String tel;//校区电话
	private String header;//负责人

	// Constructors

	/** default constructor */
	public EduDepa() {
	}

	/** full constructor */
	public EduDepa(String code, String name, String address, String tel,
			String header) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.header = header;
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

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "tel", length = 100)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "header", length = 20)
	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}