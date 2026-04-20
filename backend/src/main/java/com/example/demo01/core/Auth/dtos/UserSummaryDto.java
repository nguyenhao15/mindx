package com.example.demo01.core.Auth.dtos;

import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {
    private String _id;
    private String username;
    private String staffId;
    private String fullName;
    private String email;
    private String systemRole;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean enabled;

    private boolean credentialsNonExpired;
}
