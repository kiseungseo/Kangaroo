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
}



