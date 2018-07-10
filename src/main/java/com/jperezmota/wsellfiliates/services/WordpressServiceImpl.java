package com.jperezmota.wsellfiliates.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;
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
		validateFilterData(coupon, initialDate, initialDate);
		
		Date initialDateConverted = Date.from(initialDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date finalDateConverted = Date.from(finalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		List<CouponSell> couponSells = wordpressRepository.getSellsByCoupon(coupon, initialDateConverted, finalDateConverted);
		if(couponSells.isEmpty()) {
			throw new RuntimeException("No sales found with your Promo Code.");
		}
		
		return couponSells; 
	}
	
	private void validateFilterData(String coupon, LocalDate initialDate, LocalDate finalDate) {
		boolean validationHasErrors = false;
		String validationErrorsMessage = "\n\n";
		
		if(initialDate == null || finalDate == null || coupon == null) {
			validationHasErrors = true;
			validationErrorsMessage = "- All fields are mandatories. \n";
		}else {
			if(coupon.trim().length() <= 0) {
				validationHasErrors = true;
				validationErrorsMessage = "- Coupon field is mandatory. \n";
			}
			if(initialDate.isAfter(finalDate)){
				validationHasErrors = true;
				validationErrorsMessage = "- Initial Date field must have a value lower or equal than Final Date. \n";
			}
		}
		
		if(validationHasErrors) {
			throw new RuntimeException(validationErrorsMessage);
		}
	}
	
}
