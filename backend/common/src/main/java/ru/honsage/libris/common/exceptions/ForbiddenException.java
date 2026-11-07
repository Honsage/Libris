package ru.honsage.libris.common.exceptions;

public abstract class ForbiddenException extends BusinessException {

    public ForbiddenException(String message) {
        super(message);
    }
}
