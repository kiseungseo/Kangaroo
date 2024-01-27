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
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            	// 모든 요청 허용
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll() 
                // 특정 경로 접근 허용
            	.requestMatchers("/img/**", "/js/**", "/css/**", "/fonts/**", "/sass/**", "/chat/**").permitAll()) 
            
            // CSRF 보호 예외 설정
            .csrf((csrf) -> csrf
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))) 
            
            // 헤더 설정
            .headers((headers) -> headers
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) 
            
            // 로그인 페이지 경로 설정
            .formLogin((formLogin) -> formLogin
                    .loginPage("/user/login") 
                    // 로그인 성공 후 리다이렉트 경로 설정
                    .defaultSuccessUrl("/")) 
            
            // 로그아웃 경로 설정
            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) 
                    // 로그아웃 성공 후 리다이렉트 경로 설정
                    .logoutSuccessUrl("/") 
                    // 로그아웃 시 세션 무효화 설정
                    .invalidateHttpSession(true)) 
        ;
        return http.build();
    }
    
    
    @Bean
    // 비밀번호 인코더 Bean 설정
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    // 인증 매니저 Bean 설정
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { 
        return authenticationConfiguration.getAuthenticationManager();
    }
}
