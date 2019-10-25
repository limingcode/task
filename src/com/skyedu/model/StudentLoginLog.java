package com.skyedu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WA_StudentLoginLog")
public class StudentLoginLog {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iD;
	@Column(name = "studentId")
	private Integer studentId;
	@Column(name = "loginDate")
	private Date loginDate;
	@Column(name = "message")
	private String message;

	public StudentLoginLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentLoginLog(Integer studentId, Date loginDate, String message) {
		super();
		this.studentId = studentId;
		this.loginDate = loginDate;
		this.message = message;
	}

	public Integer getiD() {
		return iD;
	}

	public void setiD(Integer iD) {
		this.iD = iD;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
