package com.mysite.kangaroo.user;

import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.mysite.kangaroo.entity.UserProfile;
import com.mysite.kangaroo.entity.Users;
import com.mysite.kangaroo.outseideuser.CustomOAuth2UserService;
import com.mysite.kangaroo.outseideuser.FacebookUserInfo;
import com.mysite.kangaroo.outseideuser.GoogleUserInfo;
import com.mysite.kangaroo.outseideuser.KakaoUserInfo;
import com.mysite.kangaroo.outseideuser.NaverUserInfo;
import com.mysite.kangaroo.outseideuser.OAuth2UserInfo;

import jakarta.servlet.http.HttpSession;


@Service
public class UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository userProfileRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            UserProfileRepository userProfileRepository,
            CustomOAuth2UserService customOAuth2UserService) {
			this.userRepository = userRepository;
			this.passwordEncoder = passwordEncoder;
			this.userProfileRepository = userProfileRepository;
			this.customOAuth2UserService = customOAuth2UserService;
    		}
    

    // 회원가입
    public Users create(String userId, String email, String password, String name, LocalDate birth, String phone) {
        Users user = new Users();
        user.setUserId(userId);
        user.setEmail(email);
        // 비밀번호는 암호화
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(name);
        user.setBirth(birth);
        user.setPhone(phone);
        // 저장된 사용자 정보 반환
        this.userRepository.save(user);
        return user;
    }
    
  //유저 리스트
    public List<Users> getList(){
        return this.userRepository.findAll();
    }
    
    
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }
    
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

    // OAuth2 로그인
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = customOAuth2UserService.loadUser(userRequest);
        OAuth2UserInfo userInfo;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (registrationId.equals("google")) {
            userInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("facebook")) {
            userInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            Object response = oAuth2User.getAttributes().get("response");
            if (response instanceof Map) {
                userInfo = new NaverUserInfo((Map<String, Object>) response);
            } else {
                throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user_info"),
                        "Invalid user info from Naver");
            }
        } else if (registrationId.equals("kakao")) {
            userInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_service_provider"),
                    "Invalid service provider");
        }

        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();
        String email = userInfo.getEmail();

        Optional<Users> findUser = userRepository.findByProviderId(providerId);
        Users user;

        if (findUser.isEmpty()) {
            user = new Users();
            user.setUserId(provider + "_" + providerId);
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole("ROLE_USER");
            user.setProvider(provider);
            user.setProviderId(providerId);
            userRepository.save(user);
        } else {
            user = findUser.get();
        }

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("userId", user.getUserId());
        attributes.put("email", user.getEmail());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                attributes,
                "userId");
    }

}
