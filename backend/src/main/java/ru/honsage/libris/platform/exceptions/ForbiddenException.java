package ru.honsage.libris.platform.exceptions;

public abstract class ForbiddenException extends BusinessException {

    public ForbiddenException(String message) {
        super(message);
    }
}
