package com.teach.model;
// Generated 2017-7-14 14:34:49 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * TpFileUpload generated by hbm2java
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "TP_FileUpload", schema = "dbo", catalog = "Task")
public class TpFileUpload implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private int lessonId;
	private Byte fileType;
	private String fileUrl;
	private Date uploadDate;
	private Integer oaId;

	public TpFileUpload() {
	}

	public TpFileUpload(int lessonId) {
		this.lessonId = lessonId;
	}

	public TpFileUpload(int lessonId, Byte fileType, String fileUrl, Date uploadDate, Integer oaId) {
		this.lessonId = lessonId;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
		this.uploadDate = uploadDate;
		this.oaId = oaId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "lessonId", nullable = false)
	public int getLessonId() {
		return this.lessonId;
	}

	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	@Column(name = "fileType")
	public Byte getFileType() {
		return this.fileType;
	}

	public void setFileType(Byte fileType) {
		this.fileType = fileType;
	}

	@Column(name = "fileUrl")
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploadDate", length = 23)
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "oaId")
	public Integer getOaId() {
		return this.oaId;
	}

	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}

}
