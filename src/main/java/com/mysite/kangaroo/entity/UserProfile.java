package com.mysite.kangaroo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class UserProfile {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user_id;

    @Column(name = "profile_picture")
    private String profilePicture;  // UUID로 저장됩니다.

    @Column(name = "status_message")
    private String statusMessage;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;
}
