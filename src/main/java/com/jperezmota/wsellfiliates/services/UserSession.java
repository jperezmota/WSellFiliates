package com.jperezmota.wsellfiliates.services;

import java.io.Serializable;
import java.util.List;

import com.jperezmota.wsellfiliates.entity.Authority;
import com.jperezmota.wsellfiliates.entity.User;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

@SpringComponent
@VaadinSessionScope
public class UserSession implements Serializable{
	
	private boolean isAuthenticated = false;
	private String username;
	private List<String> authorities;
	
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	
	public String getUser() {
		return username;
	}
	
	public void setUser(String username) {
		this.username = username;
	}	
	
	public List<String> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	
	public void invalidateSessionData() {
		isAuthenticated = false;
		username = null;
		authorities = null;
	}
	
}
