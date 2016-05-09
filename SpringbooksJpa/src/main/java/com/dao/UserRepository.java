package com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters.isolateAggregation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.model.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>{

	User findByEmail(String email);
	
	List<User> findByIsActive(boolean isActive);
	/*@Modifying
	@Query("update userDetails u set u.isActive = ?1 where u.id = ?2")
	int setisActiveUser(String isActive, Long id);*/
	
}
