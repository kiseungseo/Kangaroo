package com.mysite.kangaroo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, String> {
	
	//사용자 ID조회
	Optional<UserDTO> findByuserId(String userId);
	
	
	
}