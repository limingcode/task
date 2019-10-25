package com.skyedu.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * WaResult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_Result")
public class WaResult implements java.io.Serializable {

	// Fields

	private Integer ID;
	//所属题目
	private WaQuestion waQuestion;
	private Timestamp createDate;
	private Timestamp modifyDate;
	//标头
	private String head;
	//答案内容
	private String content;
	//正确答案
	private String anwser;
	//答案得分
	private Double score;
	//图片标识
	private Integer isPic;

	// Constructors

	/** default constructor */
	public WaResult() {
	}

	/** full constructor */
	public WaResult(WaQuestion waQuestion, Timestamp createDate,
			Timestamp modifyDate, String head, String content, String anwser,
			Double score, Integer isPic) {
		this.waQuestion = waQuestion;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.head = head;
		this.content = content;
		this.anwser = anwser;
		this.score = score;
		this.isPic = isPic;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question", nullable = false)
	public WaQuestion getWaQuestion() {
		return this.waQuestion;
	}

	public void setWaQuestion(WaQuestion waQuestion) {
		this.waQuestion = waQuestion;
	}

	@Column(name = "createDate", nullable = false, length = 23)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "modifyDate", nullable = false, length = 23)
	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "head", nullable = false, length = 50)
	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	@Column(name = "content", nullable = false, length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "anwser", nullable = false, length = 1000)
	public String getAnwser() {
		return this.anwser;
	}

	public void setAnwser(String anwser) {
		this.anwser = anwser;
	}

	@Column(name = "score", nullable = false, precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getIsPic() {
		return isPic;
	}

	public void setIsPic(Integer isPic) {
		this.isPic = isPic;
	}

}