package com.mysite.kangaroo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 스프링 설정 클래스 선언
@Configuration 
// 웹 보안 설정 활성화
@EnableWebSecurity 
// 메소드 수준 보안 활성화
@EnableMethodSecurity(prePostEnabled = true) 

public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
    	// 보안 필터 체인 설정
    	http
    	.authorizeHttpRequests(
                authorize -> authorize
                
                .requestMatchers("/img/**", "/js/**", "/css/**", "/fonts/**", "/sass/**", "/chat/**").permitAll() // 소스 접근 허용
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                .anyRequest().authenticated()
            )  
        .csrf(cors -> cors
     		   .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
     		   .disable() // 뭐가 안 되실 경우 주석 해제 후 시도(2)
         )
        .headers(h -> h
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
         )
        .formLogin(fLogin -> fLogin
                .loginPage("/user/login")
                .defaultSuccessUrl("/kangaroo/main")
                .usernameParameter("userId")
                .passwordParameter("password")
         )
        .logout(lo -> lo
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true)
         );
        return http.build();
    }
    
    
    @Bean
    // 비밀번호 인코더 Bean 설정
    PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    // 인증 매니저 Bean 설정
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { 
        return authenticationConfiguration.getAuthenticationManager();
    }
}
