package com.mysite.kangaroo.entity;

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
@Table(name = "users")
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

//    @Column(name = "name")
//    private String userName;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "phone")
    private String phone;

    // 추가된 필드들
    @Column(name = "name")
    private String username; // 유저 이름

    @Column(name = "role")
    private String role; // 유저 권한 (일반 유저, 관리자)

    @Column(name = "provider")
    private String provider; // 공급자 (google, facebook ...)

    @Column(name = "provider_id", unique = true)
    private String providerId; // 공급자 아이디

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    // 생성자
    public Users(String userId, String password, String email,  LocalDate birth, String phone, 
                   String name, String role, String provider, String providerId) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.username = name;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    // 기본 생성자
    public Users() {
    }
}
