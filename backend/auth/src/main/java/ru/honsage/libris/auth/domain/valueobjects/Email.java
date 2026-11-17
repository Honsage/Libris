package ru.honsage.libris.auth.domain.valueobjects;

import ru.honsage.libris.auth.domain.exceptions.EmailValidationException;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public Email {
        if (value == null
                || value.isBlank()
                || !EMAIL_PATTERN.matcher(value.trim()).matches()) {
            throw new EmailValidationException(value);
        }
        value = value.trim().toLowerCase();
    }
}
