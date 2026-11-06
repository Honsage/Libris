package ru.honsage.libris.common.exceptions;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
