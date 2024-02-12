package com.mysite.kangaroo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.kangaroo.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> { 

    //사용자 ID를 기반으로 UserProfile 조회
    Optional<UserProfile> findByUser_UserId(String userId);
}
