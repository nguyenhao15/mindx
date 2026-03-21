package com.example.demo01.core.Auth.mapper;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.dtos.UserDTO;
import com.example.demo01.core.Auth.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    UserDTO formCustomUserDetailsToUserDto(CustomUserDetails customUserDetails);
}
