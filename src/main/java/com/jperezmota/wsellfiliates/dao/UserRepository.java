package com.jperezmota.wsellfiliates.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jperezmota.wsellfiliates.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
	User findByUsernameAndPassword(String username, String password);
}
