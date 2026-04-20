// CreateUserRequest.java
package com.example.demo01.core.Auth.request;

import com.example.demo01.core.Auth.dtos.WorkProfile;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "StaffId must not be blank")
    private String staffId;

    @NotBlank(message = "FullName must not be blank")
    private String fullName;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    private String systemRole;

    private StaffProfileRequestDto staffProfileRequestDto;
}
