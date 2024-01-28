package com.mysite.kangaroo.album;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    // 앨범글쓰기 들어가기
    @GetMapping("/writealbum")
    public String getWriteAlbum(Model model) {
        return "album/album_write";
    }

    public String PostWriteAlbum(Model model) {
        return "album/album_main";
    }
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
