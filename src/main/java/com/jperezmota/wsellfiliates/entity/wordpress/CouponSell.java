package com.jperezmota.wsellfiliates.entity.wordpress;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "coupon_sells")
public class CouponSell {
	
	@javax.persistence.Id
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "coupon")
	private String coupon;
	
	@Column(name = "billing_country")
	private String billingCountry;
	
	@Column(name = "billing_city")
	private String billingCity;
	
	@Column(name = "billing_state")
	private String billingState;
	
	@Column(name = "paid_date")
	private Date paidDate;
	
	@Column(name = "order_total")
	private double orderTotal;
	
	
	public CouponSell() {
		
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public String getCoupon() {
		return coupon;
	}


	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}


	public String getBillingCountry() {
		return billingCountry;
	}


	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}


	public String getBillingCity() {
		return billingCity;
	}


	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}


	public String getBillingState() {
		return billingState;
	}


	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}


	public Date getPaidDate() {
		return paidDate;
	}


	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}


	public double getOrderTotal() {
		return orderTotal;
	}


	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

}
