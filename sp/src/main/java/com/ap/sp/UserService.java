package com.ap.sp;

import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User getUserWithId10() {
		
		User user = userRepository.findOne(10);
		return user;
		
		
	}
	
}
