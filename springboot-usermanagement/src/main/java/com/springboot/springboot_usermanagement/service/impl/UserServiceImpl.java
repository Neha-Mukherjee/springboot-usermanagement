package com.springboot.springboot_usermanagement.service.impl;

import com.springboot.springboot_usermanagement.dto.UserDto;
import com.springboot.springboot_usermanagement.entity.User;
import com.springboot.springboot_usermanagement.exception.ResourceNotFoundException;
import com.springboot.springboot_usermanagement.mapper.UserMapper;
import com.springboot.springboot_usermanagement.repository.UserRepository;
import com.springboot.springboot_usermanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;




    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }



    @Override
        public UserDto createUser(UserDto userDto){

        User user = userMapper.toEntity(userDto);
        User savedUser= userRepository.save(user);
            return userMapper.toDto(savedUser);
        }

    @Override
    public UserDto getUserById(Long id) {

       User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id"+ id));
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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id"+ id));

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());

        User updatedUser=userRepository.save(user);
        return userMapper.toDto(updatedUser);

    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id"+ id));
        userRepository.delete(user);

    }




}

