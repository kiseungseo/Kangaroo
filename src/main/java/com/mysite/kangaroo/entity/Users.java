package com.mysite.kangaroo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {
	
    @Id
    @Column(length = 40)
    private String userId;

    @Column(length = 20)
    private String userName;
    
    @Column(length = 320)
    private String email;
    
    @Column(length = 64)
    private String password;

    @Column
    private LocalDateTime birthday;
    
    @Column
    private char gender;
    
    @Column(length = 13)
    private String phone;
   
    @Column
    private LocalDateTime createdAt;
    
    @Column
    private boolean secret;
    
    @Column
    private boolean friend;

}
