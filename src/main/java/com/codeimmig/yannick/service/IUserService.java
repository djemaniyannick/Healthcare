package com.codeimmig.yannick.service;

import java.util.Optional;

import com.codeimmig.yannick.model.User;

public interface IUserService {
	
	Long saveUser(User user);
	Optional<User> findByUsername(String username);
		
}
