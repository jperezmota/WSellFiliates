package com.jperezmota.wsellfiliates.services;

import java.util.List;

import com.jperezmota.wsellfiliates.entity.User;

public interface SecurityService {

	public User authenticateUser(String username, String password);
	public List<String> getUserAuthorities(User user);
	public void changeUserPassword(String username, String newPassword, String newPasswordConfirmation);
	public void validateChangePasswordData(String username, String newPassword, String newPasswordConfirmation);
}
