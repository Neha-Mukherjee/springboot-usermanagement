package com.springboot.springboot_usermanagement.controller;

import com.springboot.springboot_usermanagement.dto.UserDto;
import com.springboot.springboot_usermanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
