package ru.honsage.libris.auth.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.honsage.libris.auth.application.dto.RefreshTokenData;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Duration refreshTokenTtl;
    private static final String PREFIX = "refresh_token:";

    public RefreshTokenService(
            RedisTemplate<String, Object> redisTemplate,
            @Value("${libris.security.jwt.refresh-token-expiration}") long refreshTokenExpirationMs
    ) {
        this.redisTemplate = redisTemplate;
        this.refreshTokenTtl = Duration.ofMillis(refreshTokenExpirationMs);
    }

    public String createRefreshToken(String userId) {
        String token = UUID.randomUUID().toString();
        RefreshTokenData data = new RefreshTokenData(userId, Instant.now().plus(refreshTokenTtl));

        redisTemplate.opsForValue().set(
                PREFIX + token,
                data,
                refreshTokenTtl
        );

        return token;
    }

    public Optional<RefreshTokenData> findByToken(String token) {
        Object obj = redisTemplate.opsForValue().get(PREFIX + token);
        return Optional.ofNullable((RefreshTokenData) obj);
    }

    public void deleteToken(String token) {
        redisTemplate.delete(PREFIX + token);
    }
}