package ru.honsage.libris.auth.domain.exceptions;

import ru.honsage.libris.common.exceptions.ValidationException;

public class EmailValidationException extends ValidationException {

    public EmailValidationException(String email) {
        super("Некорректный формат email: " + email);
    }
}
