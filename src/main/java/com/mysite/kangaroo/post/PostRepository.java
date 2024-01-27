package com.mysite.kangaroo.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.kangaroo.entity.Posts;

public interface PostRepository extends JpaRepository<Posts, Long>{

}
