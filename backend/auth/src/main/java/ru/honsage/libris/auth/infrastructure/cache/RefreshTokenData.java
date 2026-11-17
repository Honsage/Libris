package ru.honsage.libris.auth.infrastructure.cache;

import java.time.Instant;

public record RefreshTokenData(
        String userId,
        Instant expiryDate
) {}