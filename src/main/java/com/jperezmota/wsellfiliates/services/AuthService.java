package com.jperezmota.wsellfiliates.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jperezmota.wsellfiliates.dao.AuthorityRepository;
import com.jperezmota.wsellfiliates.dao.UserRepository;
import com.jperezmota.wsellfiliates.entity.Authority;
import com.jperezmota.wsellfiliates.entity.User;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	AuthorityRepository authorityRepository;
	
	public User authenticateUser(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, password);
		boolean userNotFound = user == null;
		if(userNotFound) {
			throw new RuntimeException("Invalid credentials. Verify your Username and Password.");
		}
		return userRepository.findByUsernameAndPassword(username, password);
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
}
