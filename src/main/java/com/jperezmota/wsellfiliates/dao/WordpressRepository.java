package com.jperezmota.wsellfiliates.dao;

import java.util.Date;
import java.util.List;

import com.jperezmota.wsellfiliates.entity.wordpress.CouponSale;

public interface WordpressRepository {
	public List<CouponSale> getSellsByCoupon(String coupon, Date initialDate, Date finalDate);
}
