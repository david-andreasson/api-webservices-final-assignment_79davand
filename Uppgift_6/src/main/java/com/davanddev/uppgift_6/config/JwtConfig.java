package com.davanddev.uppgift_6.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for JWT settings.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {

    /**
     * Secret key used for signing JWT tokens.
     */
    private String secret;

    /**
     * JWT expiration time in milliseconds.
     */
    private long expiration;
}
