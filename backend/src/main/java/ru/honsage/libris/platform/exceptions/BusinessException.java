package ru.honsage.libris.platform.exceptions;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
