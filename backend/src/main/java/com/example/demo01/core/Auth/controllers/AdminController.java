package com.example.demo01.core.Auth.controllers;

import com.example.demo01.core.Auth.dtos.UserDTO;
import com.example.demo01.core.Auth.request.CreateUserRequest;
import com.example.demo01.core.Auth.services.UserService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hashRole('HR_MANAGER')")
public class AdminController {

    private final UserService userService;

    @PostMapping("/get-users")
    public ResponseEntity<?> getAllUsers(@RequestBody FilterWithPagination filter) {
        return new ResponseEntity<>(userService.getAllUsers(filter), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserDTO createdUser = userService.createInternalUser(createUserRequest);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> deleteUser(@PathVariable String keyword) {
        List<UserDTO> users = userService.searchUser(keyword);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update-role")
    public ResponseEntity<String> updateUserRole(@RequestParam String userId, @RequestParam String roleName) {
        userService.updateUserRole(userId, roleName);
        return ResponseEntity.ok("User role updated");
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId,
                                              @Valid @RequestBody UserDTO updateUserRequest) {
        UserDTO updatedUser = userService.updateUserInfo(userId, updateUserRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/lock-user/{staffId}")
    public ResponseEntity<UserDTO> lockUser(@PathVariable String staffId,
                                            @RequestBody boolean locked) {
        UserDTO updatedUser = userService.updateLockUser(staffId, locked);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<UserDTO> resetPassword(@PathVariable String userId) {
        UserDTO userDTO = userService.resetPassword(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{staffId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String staffId) {
        return new ResponseEntity<>(userService.getUserDtoByStaffId(staffId), HttpStatus.OK);
    }

}
