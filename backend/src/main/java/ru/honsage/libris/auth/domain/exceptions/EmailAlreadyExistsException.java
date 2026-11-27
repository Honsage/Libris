package ru.honsage.libris.auth.domain.exceptions;

import ru.honsage.libris.platform.exceptions.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {

    public EmailAlreadyExistsException(String email) {
        super("Пользователь с email: " + email + " уже существует");
    }
}
