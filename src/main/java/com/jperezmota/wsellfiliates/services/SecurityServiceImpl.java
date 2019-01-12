package com.jperezmota.wsellfiliates.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.jperezmota.wsellfiliates.dao.AsignedCouponRepository;
import com.jperezmota.wsellfiliates.dao.AuthorityRepository;
import com.jperezmota.wsellfiliates.dao.UserRepository;
import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.Authority;
import com.jperezmota.wsellfiliates.entity.User;
import com.jperezmota.wsellfiliates.utilities.HashingUtil;
import com.jperezmota.wsellfiliates.utilities.UserSession;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AsignedCouponServiceImpl asignedCouponServiceImpl;
	@Autowired 
	private AuthorityRepository authorityRepository;
	@Autowired 
	AsignedCouponRepository asignedCouponRepository;
	
	@Override
	public AsignedCoupon authenticateUser(String username, String password) {
		validateUserDataLogin(username, password);
		User authenticatedUser =  verifyUsernamePassword(username, password);
		List<String> authorities = getUserAuthorities(authenticatedUser);
		AsignedCoupon asignedCoupon = asignedCouponServiceImpl.getAsignedCouponByUsername(username);
		return asignedCoupon;
	}
	
	@Override
	public void validateUserDataLogin(String username, String password) {
		boolean validationHasError = false;
		String validationMessage = "";
		
		if(username == null || password == null) {
			validationHasError = true;
			validationMessage += "Both fields are mandatories.\n";
		}else {
			if(username.length() <= 0 || password.length() <= 0) {
				validationHasError = true;
				validationMessage += "Both fields are mandatories.\n";
			}
		}
		
		if(validationHasError) {
			throw new RuntimeException(validationMessage);
		}
	}
	
	@Override
	public User verifyUsernamePassword(String username, String password) {
		String hashedPassword = HashingUtil.hashString(password);
	
		User user = userRepository.findByUsernameAndPasswordAndEnabled(username, hashedPassword, true);
		boolean userNotFound = user == null;
		if(userNotFound) {
			throw new RuntimeException("Invalid credentials. Verify your Username and Password.");
		}
		return user;
	}
	
	@Override
	public List<String> getUserAuthorities(User user){
		List<Authority> authorities = authorityRepository.findByUser(user);
		
		boolean userWithoutAuthorities = authorities == null;
		if(userWithoutAuthorities) {
			throw new RuntimeException("User has not authorities asigned. Contact an Administrator.");
		}
		
		List<String> authoritiesList = new ArrayList<String>();
		for(Authority authority: authorities){
			authoritiesList.add(authority.getAuthority());
		}
		
		return authoritiesList;
	}
	
	@Override
	public void changeUserPassword(String username, String newPassword, String newPasswordConfirmation) {
		validateChangePasswordData(username, newPassword, newPasswordConfirmation);
		String hashedPassword = HashingUtil.hashString(newPassword);
		userRepository.changeUsernamePassword(hashedPassword, username);
	}
	
	@Override
	public void validateChangePasswordData(String username, String newPassword, String newPasswordConfirmation) {
		boolean validationFailed = false;
		String validationMessages = "\n\n";
		
		if(username == null || username.trim().length() < 1) {
			validationFailed = true;
			validationMessages += "- A Username is required.\n";
		}else {
			if(!userRepository.findById(username).isPresent()) {
				validationFailed = true;
				validationMessages += "- Invalid username.\n";
			}
		}

		if(newPassword == null || newPassword.trim().length() < 1) {
			validationFailed = true;
			validationMessages += "- New Password field is required.\n";
		}
		
		if(newPasswordConfirmation == null || newPasswordConfirmation.trim().length() < 1) {
			validationFailed = true;
			validationMessages += "- New Password Confirmation field is required.\n";
		}
		
		if(!newPassword.equals(newPasswordConfirmation)) {
			validationFailed = true;
			validationMessages += "- Both passwords must be identical.\n";
		}
		
		if(validationFailed) {
			throw new RuntimeException(validationMessages);
		}
	}

}
