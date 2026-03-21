package com.example.demo01.core.Auth.services;

import com.example.demo01.core.Auth.dtos.UserDTO;
import com.example.demo01.core.Auth.request.CreateUserRequest;
import com.example.demo01.core.Auth.request.LoginRequest;
import com.example.demo01.core.Auth.response.LoginResponse;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;

import java.util.List;

public interface UserService {
    UserDTO createInternalUser(CreateUserRequest createUserRequest);

    LoginResponse login(LoginRequest loginRequest);

    List<UserDTO> searchUser(String keyword);

    String logout();

    BasePageResponse<UserDTO> getAllUsers(FilterWithPagination filter);

    String refreshToken();

    UserDTO getUserInfo(String username);

    Boolean isGlobalUser();

    List<String> getAllowedBus();

    UserDTO getUserByStaffId(String staffId);

    void updateUserRole(String userId, String roleName);
}
