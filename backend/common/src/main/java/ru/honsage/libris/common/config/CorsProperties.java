package ru.honsage.libris.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "libris.security.cors")
public record CorsProperties(List<String> allowedOrigins) {}