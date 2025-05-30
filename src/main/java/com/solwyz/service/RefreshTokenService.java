package com.solwyz.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solwyz.entity.RefreshToken;
import com.solwyz.repo.RefreshTokenRepository;



@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        return refreshTokenRepo.save(token);
    }
}
