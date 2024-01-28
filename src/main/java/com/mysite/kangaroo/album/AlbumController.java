package com.mysite.kangaroo.album;

import com.mysite.kangaroo.entity.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    // 앨범글쓰기 들어가기
    @GetMapping("/writealbum")
    public String getWriteAlbum(Model model) {
        return "album/album_write";
    }

    // 앨범글쓰기 작성로직
    @PostMapping("/writealbum")
    public String PostWriteAlbum(@RequestBody Map<String, String> requestBody, Model model) {
        //임시로 아이디를 받아서 작성
        //나중에 직접아이디 받아와야됨
        // hashtag 미입력
        // 이미지 미입력
        // 글공개 단계 임시로 공개로
        Posts posts = new Posts();

        System.out.println(requestBody.get("testId"));
        System.out.println(requestBody.get("content"));
        posts.setContent(requestBody.get("content"));
        posts.setFriend(false);
        posts.setSecret(false);
        posts.setCreatedAt(LocalDateTime.now());
        this.albumService.saveAlbum(posts);
        System.out.println("작성완료");
        return "album/album_main";
    }

    //사진첩 테스트
    @GetMapping("/main")
    public String albumMain(Model model){
        //샘플 데이터
        List<Integer> imgList = new ArrayList<Integer>();
        int testNumber = 1;
        for(int i=0;i<10;i++){
            imgList.add(testNumber);
        }

        // ListTest
        for(int i:imgList){
            System.out.println(i);
        }

        model.addAttribute("imgList",imgList);

        return "album/album_main";
    }

        @GetMapping("/albumtest")
        public String albumTest(){
            return "album/albumtest";
    }
}
