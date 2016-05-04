package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.model.Books;

@Transactional
public interface BooksRepository extends CrudRepository<Books, Long>{

}
