package ru.honsage.libris.common.exceptions;

public abstract class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message);
    }
}
