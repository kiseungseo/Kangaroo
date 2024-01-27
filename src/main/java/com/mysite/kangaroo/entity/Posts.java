package com.mysite.kangaroo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Posts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	
	@Column
	private String hashtag;
	
	@Column
	private String content;
	
	@Column
	private String image;
	
	@Column
	private String link;

	@Column
	private Boolean secret;
	
	@Column
	private Boolean friend;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	
}
