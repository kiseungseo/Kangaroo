package com.mysite.kangaroo.outseideuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.kangaroo.entity.OutsideUsers; 

public interface OutSideRepository extends JpaRepository<OutsideUsers, Long> { 
	
    Optional<OutsideUsers> findByName(String name);
}
