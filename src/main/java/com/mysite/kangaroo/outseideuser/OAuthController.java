package com.mysite.kangaroo.outseideuser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OAuthController {
    @GetMapping("/loginForm")
    public String home() {
        return "user/loginForm";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "user/privatePage";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "user/adminPage";
    }
}