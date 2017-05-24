package com.nhb.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.nhb.domain.AuthorizationInfo;
import com.nhb.domain.user.Useracc;

public class SessionCache {
	private String id;
	private String client;
	private Useracc user;
	
	private String httpSessionId;
	private String language; //="HU";
	private Date valueDate=new Date();
	private List <GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
	private AuthorizationInfo authorizationInfo=null;
	private Serializable authenticationToken;
	public SessionCache(String id){
		this.id=id;
	}

	public List<GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}
	public void setGrantedAuthorities(List <GrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}
	@Override
	public int hashCode() {
		return id.length();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof SessionCache)){
			return false;
		}
		return ((SessionCache)obj).id.equals(this.id);
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public AuthorizationInfo getAuthorizationInfo() {
		return authorizationInfo;
	}

	public void setAuthorizationInfo(AuthorizationInfo authorizationInfo) {
		this.authorizationInfo = authorizationInfo;
	}

	public String getHttpSessionId() {
		return httpSessionId;
	}

	public void setHttpSessionId(String httpSessionId) {
		this.httpSessionId = httpSessionId;
	}

	public Useracc getUser() {
		return user;
	}

	public void setUser(Useracc user) {
		this.user = user;
	}

	public Serializable getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(Serializable authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

}
