package com.mysite.kangaroo.user;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    //회원가입
    public UserDTO create(String userId, String email, String password, String userName, LocalDate birth, String phone) {
        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserName(userName);
        user.setBirth(birth);
        user.setPhone(phone);
        this.userRepository.save(user);
        return user;
    }
    
}
