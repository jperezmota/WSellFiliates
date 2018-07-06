package com.jperezmota.wsellfiliates.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jperezmota.wsellfiliates.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
	User findByUsernameAndPasswordAndEnabled(String username, String password, boolean enabled);
	
	@Modifying
	@Query("UPDATE User u SET u.password = ?1 where u.username = ?2")
	void changeUsernamePassword(String password, String username);
	
}
