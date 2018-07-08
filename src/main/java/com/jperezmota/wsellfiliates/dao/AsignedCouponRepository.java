package com.jperezmota.wsellfiliates.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.User;

@Repository
public interface AsignedCouponRepository extends CrudRepository<AsignedCoupon, Long>{

	@Modifying
	@Query("UPDATE AsignedCoupon ac SET ac.deleted = ?1, ac.deletedDate = ?2, ac.deletedBy = ?3 WHERE ac.id = ?4")
	void deleteAsignedCoupon(boolean deleted, Date deletedDate, User user, long asignedCouponId);
	
	List<AsignedCoupon> findByDeleted(boolean deleted);
	
	AsignedCoupon findByAsignedToUsername(String username);

}
