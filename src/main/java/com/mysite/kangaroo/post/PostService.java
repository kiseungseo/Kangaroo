package com.mysite.kangaroo.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.kangaroo.entity.Posts;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	//게시글 리스트
	public List<Posts> getList(){
		return this.postRepository.findAll();
	}
}
