package com.jperezmota.wsellfiliates.dao;

import java.util.Date;
import java.util.List;

import com.jperezmota.wsellfiliates.entity.wordpress.CouponSell;

public interface WordpressRepository {
	public List<CouponSell> getSellsByCoupon(String coupon, Date initialDate, Date finalDate);
}
