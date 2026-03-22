package com.example.demo01.core.Auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String _id;
    private String username;
    private String staffId;
    private String fullName;
    private String email;
    private String systemRole;
    private List<WorkProfile> workProfileList;

    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean enabled;

    private boolean credentialsNonExpired;

    //    private LocalDate credentialsExpiryDate;
//    private LocalDate accountExpiryDate;
//    private String twoFactorSecret;
//    private String signUpMethod;

    private boolean isTwoFactorEnabled;
    private Instant createdDate;
    private Instant updatedDate;
}
