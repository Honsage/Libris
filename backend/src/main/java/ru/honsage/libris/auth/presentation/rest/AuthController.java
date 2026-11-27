package ru.honsage.libris.auth.presentation.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.honsage.libris.auth.application.dto.JwtAuthTokens;
import ru.honsage.libris.auth.application.services.AuthService;
import ru.honsage.libris.auth.domain.models.Role;
import ru.honsage.libris.auth.presentation.rest.dto.AuthResponse;
import ru.honsage.libris.auth.presentation.rest.dto.LoginRequest;
import ru.honsage.libris.auth.presentation.rest.dto.RefreshRequest;
import ru.honsage.libris.auth.presentation.rest.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        JwtAuthTokens authTokens = authService.register(request.email(), request.password(), Role.READER);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(authTokens.access(), authTokens.refresh()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        JwtAuthTokens authTokens = authService.login(request.email(), request.password());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse(authTokens.access(), authTokens.refresh()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        JwtAuthTokens authTokens = authService.refresh(request.refreshToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthResponse(authTokens.access(), authTokens.refresh()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
