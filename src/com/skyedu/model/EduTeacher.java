package com.skyedu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EduTeacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Edu_Teacher")
public class EduTeacher implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;//老师名称
	private String englishName;//英文名
	private String cnName;//真实姓名
	private String sex;//性别
	private String education;//学历
	private String tel;//联系电话
	private String jobTitle;//岗位
	private String eduSchool;//毕业学校
	private String native_;//户籍
	private Integer status;//状态 5离职
	private Integer oaId;//oa系统id
	private String wxOpenId;//微信唯一识别码

	// Constructors

	/** default constructor */
	public EduTeacher() {
	}

	/** minimal constructor */
	public EduTeacher(Integer oaId) {
		this.oaId = oaId;
	}

	/** full constructor */
	public EduTeacher(String name, String englishName, String cnName,
			String sex, String education, String tel, String jobTitle,
			String eduSchool, String native_, Integer status, Integer oaId) {
		this.name = name;
		this.englishName = englishName;
		this.cnName = cnName;
		this.sex = sex;
		this.education = education;
		this.tel = tel;
		this.jobTitle = jobTitle;
		this.eduSchool = eduSchool;
		this.native_ = native_;
		this.status = status;
		this.oaId = oaId;
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

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "englishName", length = 30)
	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@Column(name = "cnName", length = 20)
	public String getCnName() {
		return this.cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	@Column(name = "sex", length = 2)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "education", length = 20)
	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "tel", length = 13)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "jobTitle", length = 20)
	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "eduSchool", length = 50)
	public String getEduSchool() {
		return this.eduSchool;
	}

	public void setEduSchool(String eduSchool) {
		this.eduSchool = eduSchool;
	}


	@Column(name = "native", length = 40)
	public String getNative_() {
		return this.native_;
	}

	public void setNative_(String native_) {
		this.native_ = native_;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "oaId", nullable = false)
	public Integer getOaId() {
		return this.oaId;
	}

	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}

	@Column(name = "wxOpenId", nullable = false)
	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

}