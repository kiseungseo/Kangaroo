package com.mysite.kangaroo.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	//회원가입
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup";
    }
    
    //회원가입 from
    @PostMapping("/signup")
    public String signup(UserCreateForm userCreateForm, BindingResult bindingResult) {
        // 사용자 이름 필드 검증
        if (userCreateForm.getUserId() == null || userCreateForm.getUserId().trim().isEmpty()) {
            bindingResult.rejectValue("userId", "userId.required", "사용자ID는 필수항목입니다.");
            return "user/signup";
        }

        // 비밀번호 필드 검증
        if (userCreateForm.getPassword1() == null || userCreateForm.getPassword1().trim().isEmpty()) {
            bindingResult.rejectValue("password1", "password.required", "비밀번호는 필수항목입니다.");
            return "user/signup";
        }

        // 비밀번호 확인 필드 검증
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup";
        }

        // 이메일 필드 검증
        if (userCreateForm.getEmail() == null || userCreateForm.getEmail().trim().isEmpty()) {
            bindingResult.rejectValue("email", "email.required", "이메일은 필수항목입니다.");
            return "user/signup";
        }

        userService.create(userCreateForm.getUserId(), 
                userCreateForm.getEmail(), userCreateForm.getPassword1());

        return "redirect:/user/login";
    }
    
	//로그인 
	@GetMapping("/login")
	public String login() {
	  return "user/login";
	}
}
