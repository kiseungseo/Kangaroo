package com.mysite.kangaroo;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mysite.kangaroo.outseideuser.OAuth2LoginSuccessHandler;
import com.mysite.kangaroo.outseideuser.OAuth2UserService;


// 스프링 설정 클래스 선언
@Configuration 
// 웹 보안 설정 활성화
@EnableWebSecurity 
// 메소드 수준 보안 활성화
@EnableMethodSecurity(prePostEnabled = true) 

public class SecurityConfig implements WebMvcConfigurer{
	
	@Autowired
	private OAuth2UserService oAuth2useService;
	
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
	    // 보안 필터 체인 설정
	    http
	        .authorizeHttpRequests(
	            authorize -> authorize
	            .requestMatchers("/img/**", "/js/**", "/css/**", "/fonts/**", "/sass/**", "/chat/**").permitAll() // 소스 접근 허용
	            .requestMatchers("/profile/**").authenticated() // '/profile/**' 경로는 인증된 사용자만 접근 가능
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
		   
		    //로그인
		    .formLogin(fLogin -> fLogin
		            .loginPage("/user/login")
		            .defaultSuccessUrl("/user/")
		            .successHandler(customAuthenticationSuccessHandler) // 이 부분 추가
		            .usernameParameter("userId")
		            .passwordParameter("password")
		     )
		    //로그아웃
		    .logout(lo -> lo
		            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		            .logoutSuccessUrl("/user/login")
		            .invalidateHttpSession(true)
		     )
		    //구글 로그인
		    .oauth2Login(oauth2 -> oauth2
		            .loginPage("/loginForm")
		            .defaultSuccessUrl("/")
		            .successHandler(oAuth2LoginSuccessHandler) // 이 부분 추가
		            .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
		                .userService(oAuth2useService)
		            )
		        );
	     
		    return http.build();
	}

    
    //c: 필터
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/C:/uploads/");
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