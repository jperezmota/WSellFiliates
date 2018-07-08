package com.jperezmota.wsellfiliates.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jperezmota.wsellfiliates.dao.WordpressRepository;
import com.jperezmota.wsellfiliates.entity.wordpress.CouponSell;

@Service
@Transactional(value = "wordpressTransactionManager" )
public class WordpressServiceImpl implements WordpressService{

	@Autowired
	private WordpressRepository wordpressRepository;

	@Override
	public List<CouponSell> getSellsByCoupon(String coupon, LocalDate initialDate, LocalDate finalDate) {
		Date initialDateConverted = Date.from(initialDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date finalDateConverted = Date.from(finalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		
		List<CouponSell> couponSells = wordpressRepository.getSellsByCoupon(coupon, initialDateConverted, finalDateConverted);
		
		return couponSells; 
	}
	
}
