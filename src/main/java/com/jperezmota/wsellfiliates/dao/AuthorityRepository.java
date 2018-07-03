package com.jperezmota.wsellfiliates.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jperezmota.wsellfiliates.entity.Authority;
import com.jperezmota.wsellfiliates.entity.User;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String>{
	List<Authority> findByUser(User user);
}
