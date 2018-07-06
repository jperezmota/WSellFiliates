package com.jperezmota.wsellfiliates.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "asigned_coupons")
public class AsignedCoupon {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "coupon")
	@NotNull
	@Size(min=1, max=20)
	private String coupon;
	
	@NotNull
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "asigned_to")
	private User asignedTo;
	
	@Column(name = "enabled")
	private boolean enabled = true;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	@OneToOne()
	@JoinColumn(name = "deleted_by")
	private User deletedBy;
	
	@Column(name = "deleted_date")
	private Date deletedDate;
	
	public AsignedCoupon() {
		
	}

	public AsignedCoupon(String coupon, User asignedTo) {
		super();
		this.coupon = coupon;
		this.asignedTo = asignedTo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public User getAsignedTo() {
		return asignedTo;
	}
	
	public String getUsernameOfAsignedTo() {
		return asignedTo.getUsername();
	}

	public void setAsignedTo(User asignedTo) {
		this.asignedTo = asignedTo;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public User getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(User deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

}
