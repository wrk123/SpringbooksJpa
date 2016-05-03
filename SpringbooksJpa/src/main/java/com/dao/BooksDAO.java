package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.model.Books;

@Transactional
public interface BooksDAO extends CrudRepository<Books, Long>{

}
