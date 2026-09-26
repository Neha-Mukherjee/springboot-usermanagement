package com.springboot.springboot_usermanagement.repository;

import com.springboot.springboot_usermanagement.entity.PasswordReset;
import com.springboot.springboot_usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset,Long> {
    Optional<PasswordReset> findByOtp(String otp);
    Optional<PasswordReset> findByUser(User user);
}
