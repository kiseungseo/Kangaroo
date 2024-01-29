package com.mysite.kangaroo.user;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserDTO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(unique = true)
    private String userId;

    private String password;

    @Column(length = 200)
    private String userName;
    
    @Column(unique = true)
    private String email;
    
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    
    @Column
    private String phone;

}
