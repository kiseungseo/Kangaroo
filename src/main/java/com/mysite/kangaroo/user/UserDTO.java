package com.mysite.kangaroo.user;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserDTO {
	
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  @Id
	  @Column(name = "user_id")
	  private String userId;
	
	  @Column(name = "password")
	  private String password;
	
	  @Column(name = "email")
	  private String email;
	
	  @Column(name = "user_name")
	  private String userName;
	
	  @Column(name = "birth")
	  private LocalDate birth;
	
	  @Column(name = "phone")
	  private String phone;
	  
	  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	    private UserProfile userProfile;
	  
	
}