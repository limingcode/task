package com.skyedu.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OrderBy;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * WaQuestion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WA_Question")
public class WaQuestion implements java.io.Serializable {

	// Fields

	private Integer ID;
	//父题
	private WaQuestion waQuestion;
	private Timestamp createDate;
	private Timestamp modifyDate;
	//题型
	private Integer questionType;
	//题干
	private String title;
	//短标题
	private String shortTitle;
	//是否含有音频文件
	private Integer isVoice;
	//音频文件对象[{voiceURL:xxxx/xxx,length:1234,oriName:xxx},...]
	private String voice;
	//标准答题时间
	private Integer standardTime;
	//题目分数
	private Double score;
	//题目解析
	private String analyzation;
	//录入人oaid
	private Integer oaId;
	//援引该题的作业题
	private Set<WaWorkInfo> waWorkInfos = new HashSet<WaWorkInfo>(0);
	//子题
	private Set<WaQuestion> waQuestions = new HashSet<WaQuestion>(0);
	//答案
	private Set<WaResult> waResults = new HashSet<WaResult>(0);
	//层次
	private Integer waHierarchy;

	// Constructors

	/** default constructor */
	public WaQuestion() {
	}

	/** minimal constructor */
	public WaQuestion(Integer waHierarchy, Timestamp createDate,
			Timestamp modifyDate, Integer questionType, String title,
			String shortTitle, Integer isVoice, Integer standardTime,
			Double score, Integer oaId) {
		this.waHierarchy = waHierarchy;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.questionType = questionType;
		this.title = title;
		this.shortTitle = shortTitle;
		this.isVoice = isVoice;
		this.standardTime = standardTime;
		this.score = score;
		this.oaId = oaId;
	}

	/** full constructor */
	public WaQuestion(Integer waHierarchy, WaQuestion waQuestion,
			Timestamp createDate, Timestamp modifyDate, Integer questionType,
			String title, String shortTitle, Integer isVoice, String voice,
			Integer standardTime, Double score, String analyzation,
			Integer oaId, Set<WaWorkInfo> waWorkInfos,
			Set<WaQuestion> waQuestions, Set<WaResult> waResults) {
		this.waHierarchy = waHierarchy;
		this.waQuestion = waQuestion;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.questionType = questionType;
		this.title = title;
		this.shortTitle = shortTitle;
		this.isVoice = isVoice;
		this.voice = voice;
		this.standardTime = standardTime;
		this.score = score;
		this.analyzation = analyzation;
		this.oaId = oaId;
		this.waWorkInfos = waWorkInfos;
		this.waQuestions = waQuestions;
		this.waResults = waResults;
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

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hierarchy", nullable = false)*/
	@Column(name = "hierarchy", unique = true, nullable = false)
	public Integer getWaHierarchy() {
		return this.waHierarchy;
	}

	public void setWaHierarchy(Integer waHierarchy) {
		this.waHierarchy = waHierarchy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pId")
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

	@Column(name = "questionType", nullable = false)
	public Integer getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	@Column(name = "title", nullable = false, length = 4000)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "shortTitle", nullable = false, length = 2000)
	public String getShortTitle() {
		return this.shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	@Column(name = "isVoice", nullable = false)
	public Integer getIsVoice() {
		return this.isVoice;
	}

	public void setIsVoice(Integer isVoice) {
		this.isVoice = isVoice;
	}

	@Column(name = "voice", length = 500)
	public String getVoice() {
		return this.voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	@Column(name = "standardTime", nullable = false)
	public Integer getStandardTime() {
		return this.standardTime;
	}

	public void setStandardTime(Integer standardTime) {
		this.standardTime = standardTime;
	}

	@Column(name = "score", nullable = false, precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "analyzation", length = 1000)
	public String getAnalyzation() {
		return this.analyzation;
	}

	public void setAnalyzation(String analyzation) {
		this.analyzation = analyzation;
	}

	@Column(name = "oaId", nullable = false)
	public Integer getOaId() {
		return this.oaId;
	}

	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "waQuestion")
	public Set<WaWorkInfo> getWaWorkInfos() {
		return this.waWorkInfos;
	}

	public void setWaWorkInfos(Set<WaWorkInfo> waWorkInfos) {
		this.waWorkInfos = waWorkInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "waQuestion")
	@OrderBy("ID")
	public Set<WaQuestion> getWaQuestions() {
		return this.waQuestions;
	}

	public void setWaQuestions(Set<WaQuestion> waQuestions) {
		this.waQuestions = waQuestions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "waQuestion")
	@OrderBy("ID") 
	public Set<WaResult> getWaResults() {
		return this.waResults;
	}

	public void setWaResults(Set<WaResult> waResults) {
		this.waResults = waResults;
	}

}