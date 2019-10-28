package com.skyedu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "WA_Question")
public class Question {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer iD;
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "modifyDate")
	private Date modifyDate;
	@Column(name = "questionType")
	private Integer questionType;
	@Column(name = "title")
	private String title;
	@Column(name = "shortTitle")
	private String shortTitle;
	@Column(name = "isVoice")
	private Integer isVoice;
	@Column(name = "voice", nullable = true)
	private String voice;
	@Column(name = "standardTime")
	private Integer standardTime;
	@Column(name = "score")
	private double score;

	@Column(name = "analyzation", nullable = true)
	private String analyzation;
	@Column(name = "brief", nullable = true)
	private String brief;
	@Column(name = "oaId")
	private Integer oaId;
	@Column(name = "answerLength", nullable = true)
	private Integer answerLength;
	@Column(name = "editor")
	private Integer editor;
	@Transient
	private String answer;
	@Transient
	private Integer isListen;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hierarchy")
	private Hierarchy hierarchy;

	// 父题
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pId", nullable = true)
	private Question parentQuestion;
	// 子题列表
	@OneToMany(targetEntity = Question.class, cascade = { CascadeType.ALL }, mappedBy = "parentQuestion")
	@Fetch(FetchMode.SUBSELECT)

	@OrderBy("iD")
	private List<Question> childQuestion = new ArrayList<Question>();
	// 答案列表
	@OneToMany(targetEntity = Result.class, cascade = { CascadeType.ALL }, mappedBy = "question")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("iD")
	private List<Result> resultList = new ArrayList<Result>();


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public Integer getIsVoice() {
		return isVoice;
	}

	public void setIsVoice(Integer isVoice) {
		this.isVoice = isVoice;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public Integer getStandardTime() {
		return standardTime;
	}

	public void setStandardTime(Integer standardTime) {
		this.standardTime = standardTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getAnalyzation() {
		return analyzation;
	}

	public void setAnalyzation(String analyzation) {
		this.analyzation = analyzation;
	}

	public Integer getOaId() {
		return oaId;
	}

	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}

	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Hierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	public Question getParentQuestion() {
		return parentQuestion;
	}

	public void setParentQuestion(Question parentQuestion) {
		this.parentQuestion = parentQuestion;
	}

	public List<Question> getChildQuestion() {
		return childQuestion;
	}

	public void setChildQuestion(List<Question> childQuestion) {
		this.childQuestion = childQuestion;
	}

	public List<Result> getResultList() {
		return resultList;
	}

	public void setResultList(List<Result> resultList) {
		this.resultList = resultList;
	}


	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Integer getiD() {
		return iD;
	}

	public void setiD(Integer iD) {
		this.iD = iD;
	}

	public Integer getAnswerLength() {
		return answerLength;
	}

	public void setAnswerLength(Integer answerLength) {
		this.answerLength = answerLength;
	}

	public Integer getEditor() {
		return editor;
	}

	public void setEditor(Integer editor) {
		this.editor = editor;
	}

	public Integer getIsListen() {
		return isListen;
	}

	public void setIsListen(Integer isListen) {
		this.isListen = isListen;
	}
}
