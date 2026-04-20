package com.example.demo01.core.Auth.mapper;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Auth.dtos.UserDTO;
import com.example.demo01.core.Auth.dtos.UserSummaryDto;
import com.example.demo01.core.Auth.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    UserDTO formCustomUserDetailsToUserDto(CustomUserDetails customUserDetails);

    UserSummaryDto fromUserToUserSummaryDto(User user);

    List<UserSummaryDto> fromUsersToUserSummaryDto(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    User updateUserInfo(UserDTO userDTO,  @MappingTarget User user);
}
