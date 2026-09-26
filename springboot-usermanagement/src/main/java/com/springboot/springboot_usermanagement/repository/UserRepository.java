package com.springboot.springboot_usermanagement.repository;

import com.springboot.springboot_usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
