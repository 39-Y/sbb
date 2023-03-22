package com.mysite.sbb.user.service;

import com.mysite.sbb.domain.DataNotFoundException;
import com.mysite.sbb.user.DTO.UserCreateForm;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.entity.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SiteUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public SiteUser create(UserCreateForm user){
        return repository.save(SiteUser.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword1()))
                .build());
    }

    public SiteUser getUser(String username){
        SiteUser user = repository.findByUsername(username).orElse(null);
        if(user == null)
            throw new DataNotFoundException("site user not found");
        else
            return user;
    }

}
