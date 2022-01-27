package com.internal.service.somruinternal.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.internal.service.somruinternal.model2.UserRoleV2;
import com.internal.service.somruinternal.model2.UserV2;

public class AuthOutput {

    private long userId;

    private String name;

    private String password;

    private String email;

    private long invalidlogincount;

    private boolean active;

    private String editedBy;

    private Date modifiedOn;

    private Set<UserRoleV2> userRoles = new HashSet<UserRoleV2>();
	
	private String jwt;
	

	public AuthOutput(UserV2 user, String jWT_STRING) {
		this.userId = user.getUserId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.invalidlogincount = user.getInvalidlogincount();
		this.active = user.isActive();
		this.editedBy = user.getEditedBy();
		this.modifiedOn = user.getModifiedOn();
		this.userRoles = user.getUserRoles();
		this.jwt = jWT_STRING;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public long getInvalidlogincount() {
		return invalidlogincount;
	}


	public void setInvalidlogincount(long invalidlogincount) {
		this.invalidlogincount = invalidlogincount;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public String getEditedBy() {
		return editedBy;
	}


	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}


	public Date getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public Set<UserRoleV2> getUserRoles() {
		return userRoles;
	}


	public void setUserRoles(Set<UserRoleV2> userRoles) {
		this.userRoles = userRoles;
	}


	public String getJwt() {
		return jwt;
	}


	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
