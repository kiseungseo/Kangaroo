package com.mysite.kangaroo;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {
	
	@RequestMapping("/")
    public String home() {
        return "redirect:/user/login";
    }

}
