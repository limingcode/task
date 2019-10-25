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
@Table(name = "TP_PersonFileSendRecord", schema = "dbo", catalog = "Task")
public class TpPersonFileSendRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="fid")
	private int fid;
	
	@Column(name="serverId")
	private int serverId;
	
	@Column(name="serverAddr")
	private String serverAddr;
	
	@Column(name="status")
	private int status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sendTime")
	private Date sendTime;
	
	@Column(name="count")
	private int count;

	
	public TpPersonFileSendRecord() {
		super();
	}

	public TpPersonFileSendRecord(Integer id, int fid, int serverId, String serverAddr, int status, Date sendTime, int count) {
		super();
		this.id = id;
		this.fid = fid;
		this.serverId = serverId;
		this.serverAddr = serverAddr;
		this.status = status;
		this.sendTime = sendTime;
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
}
