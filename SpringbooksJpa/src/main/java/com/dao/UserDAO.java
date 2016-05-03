package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.model.User;

@Transactional
public interface UserDAO extends CrudRepository<User, Long>{

	public User findByEmail(String email);
	
}
