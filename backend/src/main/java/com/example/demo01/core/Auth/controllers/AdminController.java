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
@PreAuthorize("hasRole('ADMIN')")
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

    @PutMapping("/update-user/{staffId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String staffId,
                                              @Valid @RequestBody UserDTO updateUserRequest) {
        UserDTO updatedUser = userService.updateUserInfo(staffId, updateUserRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/lock-user/{staffId}")
    public ResponseEntity<UserDTO> lockUser(@PathVariable String staffId, @RequestParam boolean locked) {
        UserDTO updatedUser = userService.updateLockUser(staffId, locked);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/user/{staffId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String staffId) {
        return new ResponseEntity<>(userService.getUserDtoByStaffId(staffId), HttpStatus.OK);
    }

}
