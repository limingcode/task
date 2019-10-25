package com.skyedu.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
* 类说明
* @author  lisujing
* @version V1.0   
* @Date    2019年7月10日 下午2:25:17  
*/
@Entity
@Table(name = "WA_TeacherLoginInfo")
public class WaTeacherLoginInfo{
	private Integer id;
	private Integer oaId;
	private Date loginTime;
		
	public WaTeacherLoginInfo() {
	}
	
	public WaTeacherLoginInfo(Integer id) {
		this.id = id;
	}

	public WaTeacherLoginInfo(Integer oaId, Date loginTime) {
		this.oaId = oaId;
		this.loginTime = loginTime;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WaTeacherLoginInfo other = (WaTeacherLoginInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loginTime == null) {
			if (other.loginTime != null)
				return false;
		} else if (!loginTime.equals(other.loginTime))
			return false;
		if (oaId == null) {
			if (other.oaId != null)
				return false;
		} else if (!oaId.equals(other.oaId))
			return false;
		return true;
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
	
	@Column(name = "oaId", nullable = false)
	public Integer getOaId() {
		return oaId;
	}
	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}
	
	@Column(name = "loginTime", nullable = false)
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	
}
