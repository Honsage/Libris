package ru.honsage.libris.auth.domain.repositories;

import ru.honsage.libris.auth.domain.models.User;
import ru.honsage.libris.auth.domain.valueobjects.Email;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(Email email);
    User save(User user);
}
