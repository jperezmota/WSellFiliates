package com.jperezmota.wsellfiliates.utilities;

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
	private String coupon;
	
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}	
	
	public List<String> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	
	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public void invalidateSessionData() {
		isAuthenticated = false;
		username = null;
		authorities = null;
		coupon = null;
	}
	
}
