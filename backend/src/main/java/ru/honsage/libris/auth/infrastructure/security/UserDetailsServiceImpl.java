package ru.honsage.libris.auth.infrastructure.security;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.honsage.libris.auth.domain.models.User;
import ru.honsage.libris.auth.domain.repositories.UserRepository;
import ru.honsage.libris.auth.domain.valueobjects.Email;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        Email emailVo = new Email(email);
        User user = userRepository.findByEmail(emailVo)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail().value())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .build();
    }
}
