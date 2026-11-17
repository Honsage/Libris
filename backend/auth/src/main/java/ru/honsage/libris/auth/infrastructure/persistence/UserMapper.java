package ru.honsage.libris.auth.infrastructure.persistence;

import org.springframework.stereotype.Component;
import ru.honsage.libris.auth.domain.models.User;
import ru.honsage.libris.auth.domain.valueobjects.Email;

@Component
public class UserMapper {

    public UserMapper() {}

    public UserEntity toEntity(User domain) {
        return new UserEntity(
                domain.getId(),
                domain.getEmail().value(),
                domain.getPasswordHash(),
                domain.getRole(),
                domain.isActive()
        );
    }

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                new Email(entity.getEmail()),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.isActive()
        );
    }
}
