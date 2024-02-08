package com.mysite.kangaroo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.kangaroo.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {  // primaryKey 타입을 Long으로 변경했습니다.
	
    // 사용자 ID와 공급자 ID로 조회
    Optional<Users> findByUserId(String userId);
    
    
    Optional<Users> findByProviderId(String providerId);
}