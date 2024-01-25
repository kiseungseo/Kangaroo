package com.mysite.kangaroo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/kangaroo")
@Controller
public class MainController {
    
    @GetMapping("/main")
    public String main() {
        return "kangaroo_main";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login/kangaroo_login";
    }
   
    
    @GetMapping("/signup")
    public String signup() {
        return "login/kangaroo_signup";
    }
    
    @GetMapping("/album")
    public String album() {
        return "album/album_main";
    }
    
    @GetMapping("/albumtest")
    public String albumtest() {
        return "album/albumtest";
    }
    
    @GetMapping("/logintest")
    public String logintest() {
        return "login/logintest";
    }
}



