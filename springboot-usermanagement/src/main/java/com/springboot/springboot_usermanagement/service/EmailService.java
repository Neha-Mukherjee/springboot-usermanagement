package com.springboot.springboot_usermanagement.service;

public interface EmailService {
    void SendOtp(String email,String otp);
    void sendResetLink(String email, String token);
}
