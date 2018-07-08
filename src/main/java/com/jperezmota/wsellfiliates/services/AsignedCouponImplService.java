package com.jperezmota.wsellfiliates.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.jperezmota.wsellfiliates.dao.AsignedCouponRepository;
import com.jperezmota.wsellfiliates.dao.AuthorityRepository;
import com.jperezmota.wsellfiliates.dao.UserRepository;
import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.Authority;
import com.jperezmota.wsellfiliates.entity.User;
import com.jperezmota.wsellfiliates.utilities.RolesAuthorities;
import com.jperezmota.wsellfiliates.utilities.UserSession;

@Service
@Transactional
public class AsignedCouponImplService {
	
	@Autowired
	private AsignedCouponRepository asignedCouponRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	
	
	public AsignedCoupon getAsignedCouponByUsername(String username) {
		return asignedCouponRepository.findByAsignedToUsername(username);
	}
	public List<AsignedCoupon> getAllAsignedCoupons(){
		return asignedCouponRepository.findByDeleted(false);
	}

	public void deleteAsignedCoupon(AsignedCoupon asignedCoupon, String usernameInSession) {
		User userDeleting = userRepository.findById(usernameInSession).get();
		asignedCouponRepository.deleteAsignedCoupon(true, new Date(), userDeleting, asignedCoupon.getId()); 
	}
	
	public AsignedCoupon createAsignedCoupon(AsignedCoupon asignedCoupon) {
		validateAsignedCouponData(asignedCoupon);
		asignedCoupon = asignedCouponRepository.save(asignedCoupon);
		Authority authority = new Authority(RolesAuthorities.ROLE_AFFILIATE, asignedCoupon.getAsignedTo());
		authorityRepository.save(authority);
		return asignedCoupon;
	}
	
	private void validateAsignedCouponData(AsignedCoupon asignedCoupon) {
		boolean validationFailed = false;
		String validationMessages = "\n\n";

		String coupon = asignedCoupon.getCoupon();
		if(coupon == null || coupon.trim().length() < 1) {
			validationFailed = true;
			validationMessages += "Coupon is required.\n";
		}
		
		String username = asignedCoupon.getAsignedTo().getUsername();
		if(username == null || username.trim().length() < 1) {
			validationFailed = true;
			validationMessages += "Username is required.\n";
		}else {
			if(userRepository.findById(username).isPresent()) {
				validationFailed = true;
				validationMessages += "The username already exists.\n";
			}
		}
		
		String password = asignedCoupon.getAsignedTo().getPassword();
		if(password == null || password.trim().length() < 1) {
			validationFailed = true;
			validationMessages += "Password is required.\n";
		}
		
		if(validationFailed) {
			throw new RuntimeException(validationMessages);
		}
	}

}
