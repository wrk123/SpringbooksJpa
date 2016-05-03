package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.model.Purchases;

@Transactional
public interface PurchasesDAO extends CrudRepository<Purchases, Long>{
	
}
