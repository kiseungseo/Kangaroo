package com.mysite.kangaroo.user;

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
    private String user_id;

    @Column(name = "profile_picture")
    private String profilePicture;  // UUID로 저장됩니다.

    @Column(name = "status_message")
    private String statusMessage;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserDTO user;
}
