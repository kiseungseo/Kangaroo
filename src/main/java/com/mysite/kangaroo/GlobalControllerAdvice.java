package com.mysite.kangaroo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mysite.kangaroo.entity.Users;
import com.mysite.kangaroo.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserRepository userRepository;

    public GlobalControllerAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("users")
    public Users users(HttpServletRequest request) {
        // 세션에서 사용자 정보를 가져옵니다.
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        // 세션에 사용자 정보가 없는 경우, 데이터베이스에서 조회합니다.
        if (user == null) {
            // 이 부분은 경우에 따라서 적절히 구현하시면 됩니다.
        }

        return user;
    }
}