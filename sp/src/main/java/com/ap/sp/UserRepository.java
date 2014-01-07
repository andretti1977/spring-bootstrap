package com.ap.sp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

	List<User> findByUsername(String username);
	
}
