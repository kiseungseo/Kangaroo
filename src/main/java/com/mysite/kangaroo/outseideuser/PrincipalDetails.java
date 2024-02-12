package com.mysite.kangaroo.outseideuser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

import com.mysite.kangaroo.entity.Users;

@Getter
public class PrincipalDetails implements OAuth2User {

    // 사용자 정보를 저장하는 객체
    private Users Users;

    // OAuth2 사용자 정보를 저장하는 Map
    private Map<String, Object> attributes;

    // Users로 PrincipalDetails 객체를 생성하는 생성자
    public PrincipalDetails(Users Users) {
        this.Users = Users;
    }

    // Users와 attributes로 PrincipalDetails 객체를 생성하는 생성자
    public PrincipalDetails(Users Users, Map<String, Object> attributes) {
        this.Users = Users;
        this.attributes = attributes;
    }

    // 사용자의 권한 정보를 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new SimpleGrantedAuthority(Users.getRole()));
        return collect;
    }

    // 사용자의 이름을 반환하는 메서드
    @Override
    public String getName() {
        return Users.getUsername();
    }

    // OAuth2 사용자 정보를 반환하는 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // Users를 반환하는 메서드
    public Users getUsers() {
        return Users;
    }
}
