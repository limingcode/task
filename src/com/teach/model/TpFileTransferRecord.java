package com.teach.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "Tp_FileTransferRecord", schema = "dbo", catalog = "Task")
public class TpFileTransferRecord implements java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private int lessonId;
	
	private byte type;
	
	private int serverId;
	
	private String serverAddr;
	
	private String sendAddr;
	
	private String receiveAddr;
	
	private byte status;
	
	private Integer oaId;
	
	private Date createTime;
	
	private Date updateTime;
	
	private int countNum;
	
	private byte isDelete;
	
	public TpFileTransferRecord() {
	}

	public TpFileTransferRecord(Integer id, int lessonId, byte type, int serverId, String serverAddr, String sendAddr, String receiveAddr,
			byte status, Integer oaId, Date createTime, Date updateTime, int countNum, byte isDelete) {
		super();
		this.id = id;
		this.lessonId = lessonId;
		this.type = type;
		this.serverId = serverId;
		this.serverAddr = serverAddr;
		this.sendAddr = sendAddr;
		this.receiveAddr = receiveAddr;
		this.status = status;
		this.oaId = oaId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.countNum = countNum;
		this.isDelete = isDelete;
	}

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "lessonId")
	public int getLessonId() {
		return lessonId;
	}
	
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}
	
	@Column(name = "type")
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	@Column(name = "serverId")
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	@Column(name = "serverAddr")
	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	@Column(name = "sendAddr")
	public String getSendAddr() {
		return sendAddr;
	}

	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
	}

	@Column(name = "receiveAddr")
	public String getReceiveAddr() {
		return receiveAddr;
	}

	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}

	@Column(name = "status")
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Column(name = "oaId")
	public Integer getOaId() {
		return oaId;
	}

	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 23)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime", length = 23)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "countNum")
	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
	
	@Column(name = "isDelete")
	public byte getIsDelete() {
		return isDelete;
	}
	
	public void setIsDelete(byte isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "TpFileTransferRecord [id=" + id + ", lessonId=" + lessonId + ", type=" + type + ", serverId=" + serverId
				+ ", serverAddr=" + serverAddr + ", sendAddr=" + sendAddr + ", receiveAddr=" + receiveAddr + ", status="
				+ status + ", oaId=" + oaId + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", countNum=" + countNum + ", isDelete=" + isDelete + "]";
	}
	
}
