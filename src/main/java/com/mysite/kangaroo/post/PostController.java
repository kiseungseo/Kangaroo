package com.mysite.kangaroo.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.kangaroo.entity.Posts;
import com.mysite.kangaroo.entity.Users;
import com.mysite.kangaroo.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

	private final PostService postService;
	private final UserService userService;

	//검색화면 및 게시글메인
	@GetMapping("/main")
	public String postMain(Model model) {
		List<Users> userList = this.userService.getList();
		List<Posts> postList = this.postService.getList();
		model.addAttribute("userList",userList);
		model.addAttribute("postList",postList);
		return "post/post_main";
	}


}