package com.mysite.kangaroo.outseideuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 스프링 부트 애플리케이션임을 선언하는 어노테이션
@SpringBootApplication 
public class Oauth2GoogleApplication {

   public static void main(String[] args) {
      // SpringApplication.run 메서드를 통해 스프링 부트 애플리케이션을 실행
      SpringApplication.run(Oauth2GoogleApplication.class, args);
   }

   // BCryptPasswordEncoder 빈을 생성하는 메서드
   @Bean
   public BCryptPasswordEncoder bCryptPasswordEncoder() {
      
      return new BCryptPasswordEncoder();
   }

}
