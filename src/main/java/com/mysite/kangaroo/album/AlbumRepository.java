package com.mysite.kangaroo.album;

import com.mysite.kangaroo.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Posts, Long> {
}
