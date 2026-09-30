package com.springboot.springboot_usermanagement.repository;

import com.springboot.springboot_usermanagement.entity.PasswordResetLink;
import com.springboot.springboot_usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetLinkRepo
        extends JpaRepository<PasswordResetLink, Long> {

    Optional<PasswordResetLink> findByToken(String token);

    Optional<PasswordResetLink> findByUser(User user);
}