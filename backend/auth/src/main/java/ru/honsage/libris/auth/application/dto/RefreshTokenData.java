package ru.honsage.libris.auth.application.dto;

import java.time.Instant;

public record RefreshTokenData(
        String userId,
        Instant expiryDate
) {}