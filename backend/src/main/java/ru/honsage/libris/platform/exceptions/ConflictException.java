package ru.honsage.libris.platform.exceptions;

public abstract class ConflictException extends BusinessException {

    public ConflictException(String message) {
        super(message);
    }
}
