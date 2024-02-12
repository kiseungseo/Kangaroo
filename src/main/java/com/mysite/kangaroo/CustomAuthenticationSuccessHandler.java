package com.mysite.kangaroo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mysite.kangaroo.entity.Users;
import com.mysite.kangaroo.user.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//사용자가 로그인에 성공했을 때 실행되는 핸들러 
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;
    
    //로그인이 되었을때 새션을 담는 로직
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Authentication 객체에서 로그인한 사용자의 username을 가져옵니다.
        String username = authentication.getName();

        // username으로 UserDTO를 조회합니다.
        Users user = userRepository.findByUserId(username).orElse(null);

        // 사용자 정보를 세션에 저장합니다.
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        
        // 로그인 성공 후 리다이렉트될 URL을 설정합니다.
        response.sendRedirect("/user/");
    }
}