package com.jperezmota.wsellfiliates.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jperezmota.wsellfiliates.entity.wordpress.CouponSale;

@Repository
public class WordpressRepositoryImpl implements WordpressRepository {
	
	@PersistenceContext(unitName = "wordpressEntityManagerFactory")
    private EntityManager em;

	@Override
	public List<CouponSale> getSellsByCoupon(String coupon, Date initialDate, Date finalDate) {
		Query query = em.createQuery("from CouponSale cs where cs.coupon =:coupon and (cs.paidDate >=:initialDate and cs.paidDate <=:finalDate)");
		query.setParameter("coupon", coupon);
		query.setParameter("initialDate", initialDate);
		query.setParameter("finalDate", finalDate);
		
		List<CouponSale> couponSells = query.getResultList();
		
		return couponSells; 
	}

}
