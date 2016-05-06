package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.model.Purchases;

public interface PurchasesRepository extends CrudRepository<Purchases, Long>{
	
	//@Query("select p from Purchases p where p.userId=?1")
	List<Purchases> findByUserId(Long userId);
	//Purchases findByUserId(Long userId);
	
	//@Query("select p from Purchases p where p.isbn=?1")
	List<Purchases> findByIsbn(Long isbn);
	//Purchases findByIsbn(Long isbn);
	
	/*@Query("select p from Purchases p where p.isbn=?1 AND p.userId=?2") */
	public Optional<Purchases> findByIsbnAndUserId(Long isbn,Long userId);
	/*
	@Modifying
	@Query("update purchases p set p.isActive = ?1 where p.orderId = ?2")
	int setisActivePurchases(String isActive, Long orderId);*/
	
	
}
