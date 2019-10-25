package com.teach.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "TP_PersonFile", schema = "dbo", catalog = "Task")
public class TpPersonFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="oaId")
	private int oaId;
	
	@Column(name="fileType")
	private int fileType;
	
	@Column(name="status")
	private int status;
	
	@Column(name="fileName")
	private String fileName;
	
	@Column(name="fileSize")
	private long fileSize;
	
	@Column(name="filePath")
	private String filePath;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="uploadTime")
	private Date uploadTime;
	
	public TpPersonFile() {
		super();
	}

	public TpPersonFile(Integer id, int oaId, int fileType, int status, String fileName, long fileSize, String filePath,
			Date uploadTime) {
		super();
		this.id = id;
		this.oaId = oaId;
		this.fileType = fileType;
		this.status = status;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.uploadTime = uploadTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOaId() {
		return oaId;
	}

	public void setOaId(int oaId) {
		this.oaId = oaId;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
}
