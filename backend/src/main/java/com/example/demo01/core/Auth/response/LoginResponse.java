package com.example.demo01.core.Auth.response;

import com.example.demo01.core.Auth.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class LoginResponse {

    private UserDTO userDTO;
    private String accessToken;

}
