package com.example.demo01.core.Auth.mapper;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.dtos.UserDTO;
import com.example.demo01.core.Auth.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    UserDTO formCustomUserDetailsToUserDto(CustomUserDetails customUserDetails);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    User updateUserInfo(UserDTO userDTO,  @MappingTarget User user);
}
