package ru.honsage.libris.platform.exceptions;

public abstract class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(message);
    }
}
