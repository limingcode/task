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
 * TpLogger generated by hbm2java
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "TP_Logger", schema = "dbo", catalog = "Task")
public class TpLogger implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Byte type;
	private String content;
	private Date createDate;
	private Integer oaId;
	private String requestUrl;
	private String requestParam;

	public TpLogger() {
	}

	public TpLogger(Byte type, String content, Date createDate, Integer oaId, String requestUrl, String requestParam) {
		this.type = type;
		this.content = content;
		this.createDate = createDate;
		this.oaId = oaId;
		this.requestUrl = requestUrl;
		this.requestParam = requestParam;
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

	@Column(name = "type")
	public Byte getType() {
		return this.type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate", length = 23)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "oaId")
	public Integer getOaId() {
		return this.oaId;
	}

	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}

	@Column(name = "requestUrl")
	public String getRequestUrl() {
		return this.requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name = "requestParam")
	public String getRequestParam() {
		return this.requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

}
