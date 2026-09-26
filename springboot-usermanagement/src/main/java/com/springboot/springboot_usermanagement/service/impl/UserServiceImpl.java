package com.springboot.springboot_usermanagement.service.impl;

import com.springboot.springboot_usermanagement.dto.LoginDto;
import com.springboot.springboot_usermanagement.dto.UserDto;
import com.springboot.springboot_usermanagement.entity.PasswordReset;
import com.springboot.springboot_usermanagement.entity.User;
import com.springboot.springboot_usermanagement.exception.ResourceNotFoundException;
import com.springboot.springboot_usermanagement.mapper.UserMapper;
import com.springboot.springboot_usermanagement.repository.PasswordResetRepository;
import com.springboot.springboot_usermanagement.repository.UserRepository;
import com.springboot.springboot_usermanagement.service.EmailService;
import com.springboot.springboot_usermanagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, PasswordResetRepository passwordResetRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetRepository = passwordResetRepository;
        this.emailService = emailService;
    }


    @Override
    public UserDto createUser(UserDto userDto) {

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    //(passwordEncoder.encode(user.getPassword()));
    @Override
    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id" + id));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();

    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id" + id));

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);

    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id" + id));
        userRepository.delete(user);

    }

    @Override
    public UserDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.email()).orElseThrow(() -> new ResourceNotFoundException("User not found with email" ));
        if (passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new RuntimeException("Incorrect Password");

        }
        return userMapper.toDto(user);
    }

    @Override
    public void forgotPassword(String email) {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User not found"));

            String otp = String.valueOf(
                    (int) (Math.random() * 900000) + 100000
            );

        PasswordReset resetToken = passwordResetRepository.findByUser(user)
                .orElse(new PasswordReset());

        resetToken.setOtp(otp);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));
        resetToken.setUser(user);

        passwordResetRepository.save(resetToken);


            emailService.SendOtp(email, otp);


    }

    @Override
    public boolean verifyOtp(String otp) {
        return false;
    }

    @Override
    public void resetPassword(String otp, String newPassword) {


            PasswordReset reset = passwordResetRepository.findByOtp(otp)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid OTP"));

            if (reset.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("OTP expired");
            }

            User user = reset.getUser();

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            passwordResetRepository.delete(reset);
        }
    }




