package com.nhb.domain.user;

import com.nhb.domain.AbstractRq;
import com.nhb.domain.AuthorizationInfo;

public class UserLoginRq extends AbstractRq{
	private String login;
	private String password;
	private String ipAddress; 
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
