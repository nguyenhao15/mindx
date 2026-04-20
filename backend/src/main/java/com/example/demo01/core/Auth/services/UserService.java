package com.example.demo01.core.Auth.services;

import com.example.demo01.core.Auth.dtos.UserDTO;
import com.example.demo01.core.Auth.dtos.UserSummaryDto;
import com.example.demo01.core.Auth.models.User;
import com.example.demo01.core.Auth.request.CreateUserRequest;
import com.example.demo01.core.Auth.request.LoginRequest;
import com.example.demo01.core.Auth.response.LoginResponse;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;

import java.util.List;

public interface UserService {

    User getUserById(String id);

    User getUserByUsername(String username);

    UserDTO createInternalUser(CreateUserRequest createUserRequest);

    LoginResponse login(LoginRequest loginRequest);

    List<UserDTO> searchUser(String keyword);

    String logout();

    BasePageResponse<UserSummaryDto> getAllUsers(FilterWithPagination filter);

    String refreshToken();

    User getUserByStaffId(String staffId);

    UserDTO getUserInfo(String username);

    UserDTO createNewStaffProfile(StaffProfileRequestDto createStaffProfileRequestDto);

    UserDTO getUserDtoByStaffId(String staffId);

    void updateUserRole(String userId, String roleName);

    void updatePassword(String oldPassword, String newPassword);

    UserDTO updateLockUser(String userId, boolean locked);

    UserDTO activateUser(String updatePassword);

    UserDTO resetPassword(String userId);

    UserDTO updateUserInfo(String userId, UserDTO updateUserRequest);

    void evictUserCachesByUsername(String username);
}
