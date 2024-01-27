package com.mysite.kangaroo.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	
	@GetMapping("/main")
	public String postMain() {
		return "post/post_main";
	}
	
	
}
