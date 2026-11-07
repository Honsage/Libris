package ru.honsage.libris.common.exceptions;

public abstract class ConflictException extends BusinessException {

    public ConflictException(String message) {
        super(message);
    }
}
