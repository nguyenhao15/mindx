package com.example.demo01.core.Auth.services;

import com.example.demo01.core.Auth.models.Session;

public interface RefreshTokenService {

    Session createRefreshToken(String staffId);

    Boolean verifyRefreshToken(Session refreshToken);

    Session findByRefreshToken(String refreshToken);

    void deleteRefreshToken(String refreshToken);
}
