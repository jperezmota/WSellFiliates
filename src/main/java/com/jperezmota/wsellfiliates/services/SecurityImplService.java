package com.jperezmota.wsellfiliates.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jperezmota.wsellfiliates.dao.AsignedCouponRepository;
import com.jperezmota.wsellfiliates.dao.AuthorityRepository;
import com.jperezmota.wsellfiliates.dao.UserRepository;
import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.Authority;
import com.jperezmota.wsellfiliates.entity.User;

@Service
@Transactional
public class SecurityImplService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private AuthorityRepository authorityRepository;
	@Autowired 
	AsignedCouponRepository asignedCouponRepository;
	
	public User authenticateUser(String username, String password) {
		User user = userRepository.findByUsernameAndPasswordAndEnabled(username, password, true);
		boolean userNotFound = user == null;
		if(userNotFound) {
			throw new RuntimeException("Invalid credentials. Verify your Username and Password.");
		}
		return user;
	}
	
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
	
	public void changeUserPassword(String username, String newPassword, String newPasswordConfirmation) {
		validateChangePasswordData(username, newPassword, newPasswordConfirmation);
		System.out.println("Changing password to " + username + " pass: " + newPassword);
		userRepository.changeUsernamePassword(newPassword, username);
	}
	
	private void validateChangePasswordData(String username, String newPassword, String newPasswordConfirmation) {
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
