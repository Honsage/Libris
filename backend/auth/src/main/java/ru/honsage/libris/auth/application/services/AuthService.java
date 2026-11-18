    package ru.honsage.libris.auth.application.services;

    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import ru.honsage.libris.auth.application.dto.JwtAuthTokens;
    import ru.honsage.libris.auth.domain.exceptions.EmailAlreadyExistsException;
    import ru.honsage.libris.auth.domain.exceptions.RefreshTokenExpiredException;
    import ru.honsage.libris.auth.domain.exceptions.RefreshTokenNotFoundException;
    import ru.honsage.libris.auth.domain.models.Role;
    import ru.honsage.libris.auth.domain.models.User;
    import ru.honsage.libris.auth.domain.repositories.UserRepository;
    import ru.honsage.libris.auth.domain.valueobjects.Email;
    import ru.honsage.libris.auth.application.dto.RefreshTokenData;
    import ru.honsage.libris.common.security.JwtService;

    import java.time.Instant;

    @Service
    public class AuthService {

        private final AuthenticationManager authenticationManager;
        private final UserDetailsService userDetailsService;
        private final JwtService jwtService;
        private final RefreshTokenService refreshTokenService;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public AuthService(
                AuthenticationManager authenticationManager,
                UserDetailsService userDetailsService,
                JwtService jwtService,
                RefreshTokenService refreshTokenService,
                UserRepository userRepository,
                PasswordEncoder passwordEncoder
        ) {
            this.authenticationManager = authenticationManager;
            this.userDetailsService = userDetailsService;
            this.jwtService = jwtService;
            this.refreshTokenService = refreshTokenService;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public JwtAuthTokens register(String email, String password, Role role) {
            Email emailVo = new Email(email);
            if (userRepository.findByEmail(emailVo).isPresent()) {
                throw new EmailAlreadyExistsException(email);
            }

            User user = new User(emailVo, passwordEncoder.encode(password), role);
            User savedUser = userRepository.save(user);

            return generateTokens(savedUser.getEmail().value());
        }

        public JwtAuthTokens login(String email, String password) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return generateTokens(userDetails.getUsername());
        }

        public JwtAuthTokens refresh(String refreshToken) {
            RefreshTokenData tokenData = refreshTokenService.findByToken(refreshToken)
                    .orElseThrow(RefreshTokenNotFoundException::new);

            if (tokenData.expiryDate().isBefore(Instant.now())) {
                refreshTokenService.deleteToken(refreshToken);
                throw new RefreshTokenExpiredException();
            }

            refreshTokenService.deleteToken(refreshToken);
            return generateTokens(tokenData.userId());
        }

        public void logout(String refreshToken) {
            refreshTokenService.deleteToken(refreshToken);
        }

        private JwtAuthTokens generateTokens(String username) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = refreshTokenService.createRefreshToken(username);
            return new JwtAuthTokens(accessToken, refreshToken);
        }
    }
