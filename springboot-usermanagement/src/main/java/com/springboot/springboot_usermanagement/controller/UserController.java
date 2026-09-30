package com.springboot.springboot_usermanagement.controller;

import com.springboot.springboot_usermanagement.dto.ForgotPasswordRequest;
import com.springboot.springboot_usermanagement.dto.LoginDto;
import com.springboot.springboot_usermanagement.dto.UserDto;
import com.springboot.springboot_usermanagement.entity.PasswordResetLink;
import com.springboot.springboot_usermanagement.entity.User;
import com.springboot.springboot_usermanagement.exception.ResourceNotFoundException;
import com.springboot.springboot_usermanagement.repository.PasswordResetLinkRepo;
import com.springboot.springboot_usermanagement.repository.UserRepository;
import com.springboot.springboot_usermanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetLinkRepo passwordResetLinkRepo;

    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetLinkRepo passwordResetLinkRepo) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetLinkRepo = passwordResetLinkRepo;
    }
    // build create user REST API
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto savedUser =userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }
    // build Get user REST API
    @GetMapping("/{id}")
    public ResponseEntity<UserDto>getUserById(@PathVariable Long id){
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);

    }
    //build get all usee RESTAPI
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    //build update user rest api
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id ,@RequestBody UserDto userDto){
        UserDto updatedUser = userService.updateUser(id,userDto);
        return ResponseEntity.ok(updatedUser);
    }

    //build delete user restapi
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("user deleted successfully");
    }

    //build login request restapi
    @PostMapping("/login")
   public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    //build password reset restapi
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request){
        userService.forgotPassword(request.email());
        return ResponseEntity.ok("Otp sent to email");
    }

    //build otp verify rest api
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String otp) {
        boolean valid = userService.verifyOtp(otp);
        if (valid) {
            return ResponseEntity.ok("OTP verified");
        }
        return ResponseEntity.badRequest().body("Invalid or expired OTP");
    }
    //reset pass api
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String otp,
            @RequestParam String newPassword) {

        userService.resetPassword(otp, newPassword);

        return ResponseEntity.ok("Password reset successfully");
    }
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.updatePassword( oldPassword, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }


    @PostMapping("/forgot-password-link")
    public ResponseEntity<String> forgotPasswordByLink(
            @RequestBody ForgotPasswordRequest request) {

        userService.forgotPasswordByLink(request.email());

        return ResponseEntity.ok("Reset link sent to email");
    }


    @PostMapping("/reset-password-link")
    public ResponseEntity<String> resetPasswordByLink(
            @RequestParam String token,
            @RequestParam String newPassword) {

        userService.resetPasswordByLink(token, newPassword);

        return ResponseEntity.ok("Password reset successfully");
    }
    @GetMapping("/reset-password-link")
    public ResponseEntity<String> openResetPasswordLink(@RequestParam String token) {

        PasswordResetLink resetLink = passwordResetLinkRepo
                .findByToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid reset link"));

        if (resetLink.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Reset link expired");
        }

        return ResponseEntity.ok(
                "Reset link is valid. You can now enter your new password."
        );
    }

}
