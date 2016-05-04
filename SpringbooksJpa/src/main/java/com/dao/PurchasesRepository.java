package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.model.Purchases;

@Transactional
public interface PurchasesRepository extends CrudRepository<Purchases, Long>{
	
}
