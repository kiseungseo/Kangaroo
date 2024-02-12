package com.mysite.kangaroo;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.kangaroo.outseideuser.NaverUserInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KangarooApplicationTests {

	@Test
    void naverUserInfoTest() {
        // 테스트를 위한 가상의 네이버 사용자 정보
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("sub", "123456");
        attributes.put("name", "홍길동");
        attributes.put("email", "hong@gmail.com");

        NaverUserInfo naverUserInfo = new NaverUserInfo(attributes);

        // 각 메소드가 예상한 값을 반환하는지 확인
        assertEquals("123456", naverUserInfo.getProviderId());
        assertEquals("naver", naverUserInfo.getProvider());
        assertEquals("홍길동", naverUserInfo.getName());
        assertEquals("hong@gmail.com", naverUserInfo.getEmail());
    }
}
