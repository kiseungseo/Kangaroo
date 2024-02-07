package com.mysite.kangaroo.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.kangaroo.entity.UserDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
	
	@Autowired
	private final UserRepository userRepository;
	@Autowired
    private final PasswordEncoder passwordEncoder;
	@Autowired
    private UserProfileRepository userProfileRepository;
    
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
    
    //유저 리스트
    public List<UserDTO> getList(){
        return this.userRepository.findAll();
    }
    
    
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }
    
    public String getStatusMessage(String userId) {
        // UserDTO를 먼저 조회
        UserDTO user = userRepository.findByuserId(userId).orElse(null);
        if (user == null) {
            // userId에 해당하는 UserDTO가 없는 경우
            return null;
        }

        // UserProfile 조회
        UserProfile userProfile = userProfileRepository.findByUser_UserId(userId).orElse(null);
        if (userProfile == null) {
            // userId에 해당하는 UserProfile이 없는 경우
            return null;
        }

        // statusMessage를 반환
        return userProfile.getStatusMessage();
    }
    
    
}
