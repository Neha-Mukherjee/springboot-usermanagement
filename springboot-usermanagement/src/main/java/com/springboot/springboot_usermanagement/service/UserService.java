package com.springboot.springboot_usermanagement.service;

import com.springboot.springboot_usermanagement.dto.LoginDto;
import com.springboot.springboot_usermanagement.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long id,UserDto userDto);

    void deleteUser(Long id);

   String login(LoginDto loginDto) ;

    void forgotPassword(String email);

    boolean verifyOtp(String otp);

    void resetPassword(String otp, String newPassword);

    void updatePassword(String oldPassword, String newPassword);

    void forgotPasswordByLink(String email);

    void resetPasswordByLink(String token, String newPassword);
}