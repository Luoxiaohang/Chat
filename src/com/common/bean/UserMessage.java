package com.common.bean;

import java.io.Serializable;

public class UserMessage implements Serializable {
  
	 private Boolean hasUser=false;
	 private String userId;
	 private String pw;
	 
	 public Boolean getHasUser() {
		return hasUser;
	}

	public void setHasUser(Boolean hasUser) {
		this.hasUser = hasUser;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
  public UserMessage(String userId, String pw) {
		this.userId = userId;
		this.pw = pw;
	}

public String getUserId() {
	return userId;
}
}
