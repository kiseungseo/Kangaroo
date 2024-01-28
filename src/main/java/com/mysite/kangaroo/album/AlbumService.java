package com.mysite.kangaroo.album;

import com.mysite.kangaroo.entity.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    public void saveAlbum(Posts posts){
        this.albumRepository.save(posts);
    }
}
