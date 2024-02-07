package com.mysite.kangaroo.user;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mysite.kangaroo.entity.UserDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserDTO> _userDTO = this.userRepository.findByuserId(userId);
        UserDTO userDTO;
        
        if (_userDTO.isPresent()) {
            userDTO = _userDTO.get();
        } else {
            // 사용자 ID에 해당하는 사용자가 없을 때의 처리. 예를 들어, 새로운 UserDetails 객체를 생성하거나, 기본 사용자 정보를 사용할 수 있음.
            userDTO = new UserDTO();
            // 필요한 필드를 설정하세요.
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if ("admin".equals(userId)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        
        return new User(userDTO.getUserId(), userDTO.getPassword(), authorities);
    }
    
}