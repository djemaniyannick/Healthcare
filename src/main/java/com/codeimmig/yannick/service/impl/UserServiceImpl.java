package com.codeimmig.yannick.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.codeimmig.yannick.model.User;
import com.codeimmig.yannick.repo.UserRepository;
import com.codeimmig.yannick.service.IUserService;

public class UserServiceImpl  implements IUserService{
	@Autowired UserRepository repo;

	public UserServiceImpl() {
		
	}

	@Override
	public Long saveUser(User user) {
		return repo.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return repo.findByUsername(username);
	}

}
