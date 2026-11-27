package ru.honsage.libris.platform.exceptions;

public abstract class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message);
    }
}
