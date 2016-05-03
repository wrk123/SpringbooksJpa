package com.dao;

import org.springframework.data.repository.CrudRepository;
import com.model.Books;


public interface BooksDAO extends CrudRepository<Books, Long>{

}
