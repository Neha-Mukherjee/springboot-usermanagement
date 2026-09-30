package com.springboot.springboot_usermanagement.service.impl;


import com.springboot.springboot_usermanagement.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void SendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your password reset OTP is: " + otp
                + "\n\nThis OTP expires in 5 minutes.");

        mailSender.send(message);
    }
    @Override
    public void sendResetLink(String email, String token) {

        String resetLink = "http://localhost:8080/api/users/reset-password-link?token=\" + token;" + token;
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Password Reset Link");

        message.setText("Click the link below to reset your password:\n\n"
                        + resetLink
                        + "\n\nThis link expires in 5 minutes."
        );

        mailSender.send(message);
    }
}
