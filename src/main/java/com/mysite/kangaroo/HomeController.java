package com.mysite.kangaroo;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {
	
	//메인로그인 페이지
	@RequestMapping("/")
    public String home() {
        return "redirect:/user/login";
    }

}
