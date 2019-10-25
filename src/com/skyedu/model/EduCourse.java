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
 * 班级表（课程）
 */
@Entity
@Table(name = "Edu_Course")
public class EduCourse implements java.io.Serializable {

	// Fields

	private Integer id;//
	private String name;//班级名称
	private Integer period;//学期id
	private String depa;//校区编号
	private String grade;//年级编号
	private String subject;//科目编号
	private String cate;//层次编号
	private Integer teacher;//老师id
	private Integer expecteNum;//班额
	private Double classHours;//课时
	private Integer classNumber;//课次
	private Double bookFee;//书本费
	private Double unitClassFee;//单价
	private Date beginDate;//开始日期
	private Date endDate;//结束日期
	private Integer status;//状态 1在读 3结课 -1作废

	// Constructors

	/** default constructor */
	public EduCourse() {
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

	@Column(name = "name", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "period")
	public Integer getPeriod() {
		return this.period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	@Column(name = "depa", length = 20)
	public String getDepa() {
		return this.depa;
	}

	public void setDepa(String depa) {
		this.depa = depa;
	}

	@Column(name = "grade", length = 10)
	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "subject", length = 10)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "cate", length = 10)
	public String getCate() {
		return this.cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	@Column(name = "teacher")
	public Integer getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Integer teacher) {
		this.teacher = teacher;
	}

	@Column(name = "expecteNum")
	public Integer getExpecteNum() {
		return this.expecteNum;
	}

	public void setExpecteNum(Integer expecteNum) {
		this.expecteNum = expecteNum;
	}

	@Column(name = "classHours", precision = 53, scale = 0)
	public Double getClassHours() {
		return this.classHours;
	}

	public void setClassHours(Double classHours) {
		this.classHours = classHours;
	}

	@Column(name = "classNumber")
	public Integer getClassNumber() {
		return this.classNumber;
	}

	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}

	@Column(name = "bookFee", precision = 53, scale = 0)
	public Double getBookFee() {
		return this.bookFee;
	}

	public void setBookFee(Double bookFee) {
		this.bookFee = bookFee;
	}

	@Column(name = "unitClassFee", precision = 53, scale = 0)
	public Double getUnitClassFee() {
		return this.unitClassFee;
	}

	public void setUnitClassFee(Double unitClassFee) {
		this.unitClassFee = unitClassFee;
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

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}