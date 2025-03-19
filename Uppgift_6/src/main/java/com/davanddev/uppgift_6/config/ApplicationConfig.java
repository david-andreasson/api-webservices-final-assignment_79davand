package com.davanddev.uppgift_6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuration class for common beans to avoid circular dependencies.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Creates a BCryptPasswordEncoder bean for encoding passwords.
     *
     * @return an instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates an ObjectMapper bean for JSON processing.
     *
     * @return a new ObjectMapper instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
