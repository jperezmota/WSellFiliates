package com.jperezmota.wsellfiliates.services;

import java.time.LocalDate;
import java.util.List;

import com.jperezmota.wsellfiliates.entity.wordpress.CouponSale;

public interface WordpressService {
	public List<CouponSale> getSalesByCoupon(String coupon, LocalDate initialDate, LocalDate finalDate);
	public void validateFilterData(String coupon, LocalDate initialDate, LocalDate finalDate);
}
