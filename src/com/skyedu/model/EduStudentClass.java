package com.skyedu.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生报名表
 */
@Entity
@Table(name = "Edu_StudentClass")
public class EduStudentClass implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer student;//学生id
	private Integer course;//班级id
	private Integer courseFrom;//转出班级id
	private Integer status;//状态 1在读 3结课 4流失 5转班 8,9取消分班
	private Timestamp transferTime;
	private Timestamp recordTime;
	private Timestamp updateTime;
	private Timestamp closeTime;//结课时间
	private String recordStaff;//操作人

	// Constructors

	/** default constructor */
	public EduStudentClass() {
	}

	/** full constructor */
	public EduStudentClass(Integer student, Integer course, Integer courseFrom,
			Integer status, Timestamp transferTime, Timestamp recordTime,
			Timestamp updateTime, Timestamp closeTime, String recordStaff) {
		this.student = student;
		this.course = course;
		this.courseFrom = courseFrom;
		this.status = status;
		this.transferTime = transferTime;
		this.recordTime = recordTime;
		this.updateTime = updateTime;
		this.closeTime = closeTime;
		this.recordStaff = recordStaff;
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

	@Column(name = "student")
	public Integer getStudent() {
		return this.student;
	}

	public void setStudent(Integer student) {
		this.student = student;
	}

	@Column(name = "course")
	public Integer getCourse() {
		return this.course;
	}

	public void setCourse(Integer course) {
		this.course = course;
	}

	@Column(name = "courseFrom")
	public Integer getCourseFrom() {
		return this.courseFrom;
	}

	public void setCourseFrom(Integer courseFrom) {
		this.courseFrom = courseFrom;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "transferTime", length = 23)
	public Timestamp getTransferTime() {
		return this.transferTime;
	}

	public void setTransferTime(Timestamp transferTime) {
		this.transferTime = transferTime;
	}

	@Column(name = "recordTime", length = 23)
	public Timestamp getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	@Column(name = "updateTime", length = 23)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "closeTime", length = 23)
	public Timestamp getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}

	@Column(name = "recordStaff", length = 20)
	public String getRecordStaff() {
		return this.recordStaff;
	}

	public void setRecordStaff(String recordStaff) {
		this.recordStaff = recordStaff;
	}

}