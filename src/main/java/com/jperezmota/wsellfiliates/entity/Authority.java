package com.jperezmota.wsellfiliates.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "authorities")
public class Authority {
	
	@Id
	@Column(name = "authority")
	@NotNull
	@Size(min = 1, max = 50)
	private String authority;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "username")
	@NotNull
	private User user;
	
	public Authority() {
		
	}
	
	public Authority(String authority, User user) {
		super();
		this.authority = authority;
		this.user = user;
	}


	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Authority [authority=" + authority + ", user=" + user + "]";
	}
	
	

}
