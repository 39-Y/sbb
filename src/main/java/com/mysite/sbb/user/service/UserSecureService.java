package com.mysite.sbb.user.service;

import com.mysite.sbb.user.Role.UserRole;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.entity.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserSecureService implements UserDetailsService {
    final SiteUserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SiteUser siteUser = repository.findByUsername(username).orElse(null);
        if(siteUser == null)
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username))
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
