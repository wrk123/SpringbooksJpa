package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.model.Books;

public interface BooksRepository extends CrudRepository<Books, Long>{

	@Query("select p from Purchases p")
	public List<Books> findAll();
	
	
	
}
