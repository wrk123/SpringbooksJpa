package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.model.Books;

public interface BooksRepository extends CrudRepository<Books, Long>{

	
	public List<Books> findAll();
	
	/*@Modifying
	@Query("update book b set b.isActive = ?1 where b.isbn = ?2")
	int setisActiveBook(String isActive, Long isbn);
	
	*/
	
}
