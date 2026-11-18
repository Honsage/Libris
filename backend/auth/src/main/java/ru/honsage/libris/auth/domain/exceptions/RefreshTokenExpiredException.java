package ru.honsage.libris.auth.domain.exceptions;

import ru.honsage.libris.common.exceptions.BusinessException;

public class RefreshTokenExpiredException extends BusinessException {

    public RefreshTokenExpiredException() {
        super("Срок действия Refresh token истек");
    }
}
