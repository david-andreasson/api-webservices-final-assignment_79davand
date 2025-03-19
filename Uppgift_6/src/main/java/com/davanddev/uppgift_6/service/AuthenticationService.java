package com.davanddev.uppgift_6.service;

import com.davanddev.uppgift_6.dto.AuthenticationRequest;
import com.davanddev.uppgift_6.dto.AuthenticationResponse;
import com.davanddev.uppgift_6.dto.RegisterRequest;
import com.davanddev.uppgift_6.model.User;
import com.davanddev.uppgift_6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for handling authentication operations such as registration, login,
 * and processing OAuth2 users.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user and returns an authentication response with a JWT token.
     *
     * @param request the registration request containing user details.
     * @return an authentication response containing the JWT token.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider("local")
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user using email and password and returns an authentication response.
     *
     * @param request the authentication request containing credentials.
     * @return an authentication response containing the JWT token.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Handles OAuth2 user login by creating or updating the user and generating a JWT token.
     *
     * @param oauth2User the OAuth2 user.
     * @return a JWT token.
     */
    @Transactional
    public String handleOAuth2User(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createOAuth2User(oauth2User));

        // Update user info if needed
        updateUserInfo(user, oauth2User);

        return jwtService.generateToken(user);
    }

    /**
     * Creates a new user based on OAuth2 user attributes.
     *
     * @param oauth2User the OAuth2 user.
     * @return the newly created user.
     */
    private User createOAuth2User(OAuth2User oauth2User) {
        User user = User.builder()
                .email(oauth2User.getAttribute("email"))
                .firstName(oauth2User.getAttribute("given_name"))
                .lastName(oauth2User.getAttribute("family_name"))
                .provider("google")
                .password(passwordEncoder.encode("")) // OAuth2 users do not require a password.
                .build();
        return userRepository.save(user);
    }

    /**
     * Updates the user's information based on the provided OAuth2 user attributes.
     *
     * @param user the existing user.
     * @param oauth2User the OAuth2 user.
     */
    private void updateUserInfo(User user, OAuth2User oauth2User) {
        user.setFirstName(oauth2User.getAttribute("given_name"));
        user.setLastName(oauth2User.getAttribute("family_name"));
        userRepository.save(user);
    }
}
