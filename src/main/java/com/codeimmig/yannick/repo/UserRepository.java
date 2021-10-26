package com.codeimmig.yannick.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeimmig.yannick.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	Optional<User> findByUsername(String username);
}
