package com.mysite.kangaroo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "outsideUsers")
public class OutsideUsers {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id; //기본키
	  
	  @Column(name = "name")
	  private String name; //유저 이름
	
	  @Column(name = "password")
	  private String password; //유저 비밀번호
	
	  @Column(name = "email")
	  private String email; //유저 구글 이메일
	
	  @Column(name = "role")
	  private String role; //유저 권한 (일반 유저, 관리지ㅏ)
	  
	  @Column(name = "provider")
	  private String provider; //공급자 (google, facebook ...)
	  
	  @Column(name = "provider_id")
	  private String providerId; //공급 아이디
}
