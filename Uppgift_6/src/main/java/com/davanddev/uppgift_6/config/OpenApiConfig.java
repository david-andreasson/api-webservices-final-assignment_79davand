package com.davanddev.uppgift_6.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates a custom OpenAPI instance with security schemes and API metadata.
     *
     * @return the OpenAPI configuration object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", bearerScheme))
                .info(new Info()
                        .title("API for Assignment 6")
                        .version("1.0")
                        .description("API with authentication and rate limiting"))
                .addSecurityItem(securityRequirement);
    }
}
