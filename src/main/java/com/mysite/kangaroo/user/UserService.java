package com.mysite.kangaroo.user;

import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.mysite.kangaroo.entity.Users;
import com.mysite.kangaroo.entity.UserProfile;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserProfileRepository userProfileRepository;

    // 회원가입
    public Users create(String userId, String email, String password, String userName, LocalDate birth, String phone) {
        Users user = new Users();
        user.setUserId(userId);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserName(userName);
        user.setBirth(birth);
        user.setPhone(phone);
        this.userRepository.save(user);
        return user;
    }

    // OAuth2 로그인
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = userRequest.getClientRegistration().getRegistrationId();
        String email = userRequest.getClientRegistration().getRegistrationId();
        String role = "ROLE_USER";

        Optional<Users> findUser = userRepository.findByProviderId(providerId);
        Users user;
        if (findUser.isEmpty()) {
            user = new Users();
            user.setUserId(provider + "_" + providerId); // 유저 아이디 설정
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password")); // 임의의 비밀번호 설정
            user.setRole(role);
            user.setProvider(provider);
            user.setProviderId(providerId);
            userRepository.save(user);
        } else {
            user = findUser.get();
        }

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("userId", user.getUserId());
        attributes.put("email", user.getEmail());
        // ... 필요한 속성을 더 추가하세요 ...

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                attributes,
                "userId");
    }

    // 유저 리스트
    public List<Users> getList() {
        return this.userRepository.findAll();
    }

    // 로그인 여부
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    // 상태 메시지
    public String getStatusMessage(String userId) {
        // UserDTO를 먼저 조회
        Users user = userRepository.findByUserId(userId).orElse(null);
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
