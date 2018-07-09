package com.jperezmota.wsellfiliates.services;

import java.util.List;

import com.jperezmota.wsellfiliates.entity.AsignedCoupon;

public interface AsignedCouponService {
	
	public AsignedCoupon getAsignedCouponByUsername(String username);
	public List<AsignedCoupon> getAllAsignedCoupons();
	public void deleteAsignedCoupon(AsignedCoupon asignedCoupon, String usernameInSession);
	public AsignedCoupon createAsignedCoupon(AsignedCoupon asignedCoupon);
	public void validateAsignedCouponData(AsignedCoupon asignedCoupon);

}
