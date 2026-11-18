package ru.honsage.libris.auth.domain.models;

import ru.honsage.libris.auth.domain.valueobjects.Email;

import java.util.UUID;

public class User {
    private final UUID id;
    private final Email email;
    private final String passwordHash;
    private final Role role;
    private final boolean isActive;

    public User(UUID id, Email email, String passwordHash, Role role, boolean isActive) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isActive = isActive;
    }

    public User(Email email, String passwordHash, Role role) {
        this(UUID.randomUUID(), email, passwordHash, role, true);
    }


    public UUID getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActive() {
        return isActive;
    }
}
