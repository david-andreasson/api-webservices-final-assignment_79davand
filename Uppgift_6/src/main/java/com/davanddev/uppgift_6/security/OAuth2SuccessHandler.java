package com.davanddev.uppgift_6.security;

import com.davanddev.uppgift_6.service.JwtService;
import com.davanddev.uppgift_6.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Handles successful OAuth2 authentication by creating or updating a user and generating a JWT token.
 */
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Retrieve the OAuth2 user from the authentication object
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Create or update the user
        var user = userService.createOAuthUser(oAuth2User);

        // Generate a JWT token for the user
        var token = jwtService.generateToken(user);

        // Prepare and write the JSON response
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(Map.of(
                "token", token,
                "email", user.getEmail(),
                "name", user.getFirstName() + " " + user.getLastName()
        )));
    }
}
