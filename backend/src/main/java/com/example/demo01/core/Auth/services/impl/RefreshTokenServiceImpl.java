package com.example.demo01.core.Auth.services.impl;

import com.example.demo01.core.Auth.models.Session;
import com.example.demo01.core.Auth.repositories.SessionRepository;
import com.example.demo01.core.Auth.services.RefreshTokenService;
import com.example.demo01.core.Exceptions.InvalidCredentialsException;
import com.example.demo01.core.Security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${spring.app.jwt.refresh-expiration}")
    int refreshExpirationMs;

    private final SessionRepository sessionRepository;

    private final JwtUtils jwtUtils;

    @Override
    public Session createRefreshToken(String staffId) {
        Session existSession = sessionRepository.findByStaffId(staffId);
        if (existSession != null) {
            return existSession;
        }
        Session session = new Session();
        Instant expiryDate = Instant.now().plusMillis(refreshExpirationMs);
        String refreshToken = jwtUtils.generateRefreshToken(staffId);

        session.setStaffId(staffId);

        session.setExpiryDate(expiryDate);
        session.setRefreshToken(refreshToken);

        session = sessionRepository.save(session);

        return session;
    }

    @Override
    public Boolean verifyRefreshToken(Session refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            sessionRepository.delete(refreshToken);
            throw new InvalidCredentialsException("Refresh token was expired. Please make a new signin request");
        }
        return true;
    }

    @Override
    public Session findByRefreshToken(String refreshToken) {
        Session userSession = sessionRepository.findByRefreshToken(refreshToken);
        if (userSession == null) {
            throw new InvalidCredentialsException("Invalid token");
        }
        return userSession;
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        sessionRepository.deleteByRefreshToken(refreshToken);
    }

    @Override
    public void deleteRefreshTokenByUserId(String userId) {
        Session userSession = sessionRepository.findByStaffId(userId);
        if (userSession != null) {
            sessionRepository.delete(userSession);
        }
    }
}
