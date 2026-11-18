package ru.honsage.libris.auth.presentation.rest.dto;

import ru.honsage.libris.auth.domain.models.Role;

public record RegisterRequest(String email, String password) {}
