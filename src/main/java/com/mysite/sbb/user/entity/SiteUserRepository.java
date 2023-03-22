package com.mysite.sbb.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(String username);
}
