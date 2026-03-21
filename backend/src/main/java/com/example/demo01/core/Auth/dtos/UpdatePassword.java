package com.example.demo01.core.Auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePassword {
    private String currentPassword;
    private String newPassword;
}
