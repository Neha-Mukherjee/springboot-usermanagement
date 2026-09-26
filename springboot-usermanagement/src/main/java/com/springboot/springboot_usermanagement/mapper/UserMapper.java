package com.springboot.springboot_usermanagement.mapper;

import com.springboot.springboot_usermanagement.dto.UserDto;
import com.springboot.springboot_usermanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user){
        return new UserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());

    }
    public User toEntity(UserDto userDto){
        return new User(userDto.id(), userDto.firstName(),userDto.lastName(),userDto.email());

    }

}
