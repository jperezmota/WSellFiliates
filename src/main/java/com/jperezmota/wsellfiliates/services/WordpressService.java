package com.jperezmota.wsellfiliates.services;

import java.time.LocalDate;
import java.util.List;

import com.jperezmota.wsellfiliates.entity.wordpress.CouponSell;

public interface WordpressService {
	public List<CouponSell> getSellsByCoupon(String coupon, LocalDate initialDate, LocalDate finalDate);
}
