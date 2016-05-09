package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.model.Purchases;

public interface PurchasesRepository extends CrudRepository<Purchases, Long>{
	
	
	List<Purchases> findByUserId(Long userId);
	
	List<Purchases> findByIsbn(Long isbn);
	
	public Optional<Purchases> findByIsbnAndUserId(Long isbn,Long userId);
	
	List<Purchases> findByIsActive(boolean isActive);
	
}
