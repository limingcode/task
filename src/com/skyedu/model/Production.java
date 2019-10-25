package com.skyedu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "WA_Production")
public class Production {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iD;
	@Column(name = "title",nullable=true)
	private String title;
	@Column(name = "content",nullable=true)
	private String content;
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "student")
	private Integer student;
	@Column(name = "love")
	private Integer love;
	@Column(name = "readed")
	private Integer readed;
	@Column(name = "type")
	private Integer type;

	@OneToMany(targetEntity = Attachment.class, cascade = { CascadeType.ALL }, mappedBy = "production")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("iD")
	private List<Attachment> attachments = new ArrayList<Attachment>();


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStudent() {
		return student;
	}

	public void setStudent(Integer student) {
		this.student = student;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Integer getLove() {
		return love;
	}

	public void setLove(Integer love) {
		this.love = love;
	}

	public Integer getReaded() {
		return readed;
	}

	public Integer getiD() {
		return iD;
	}

	public void setiD(Integer iD) {
		this.iD = iD;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setReaded(Integer readed) {
		this.readed = readed;
	}
}
