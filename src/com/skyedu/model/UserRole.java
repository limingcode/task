package com.skyedu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WA_UserRole")
public class UserRole {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iD;
	@Column(name = "oaId")
	private Integer oaId;
	@Column(name = "roleId")
	private Integer roleId;
	@Column(name = "state")
	private Integer state; //0失效 1正常
	
	public UserRole(Integer oaId, Integer roleId, Integer state) {
		super();
		this.oaId = oaId;
		this.roleId = roleId;
		this.state = state;
	}
	public UserRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getiD() {
		return iD;
	}
	public void setiD(Integer iD) {
		this.iD = iD;
	}
	public Integer getOaId() {
		return oaId;
	}
	public void setOaId(Integer oaId) {
		this.oaId = oaId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}