package ru.honsage.libris.auth.domain.exceptions;

import ru.honsage.libris.platform.exceptions.NotFoundException;

public class RefreshTokenNotFoundException extends NotFoundException {

    public RefreshTokenNotFoundException() {
        super("Refresh token не найден");
    }
}
