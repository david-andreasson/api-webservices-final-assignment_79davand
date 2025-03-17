package com.davanddev.quizapp_api.service;

import com.davanddev.quizapp_api.models.UserEntity;
import com.davanddev.quizapp_api.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    // List of allowed email addresses; leave empty or remove check to allow all emails.
    private final List<String> allowedEmails = Arrays.asList();

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Delegate to the default service to load user details from Google
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // Extract email from OAuth2User attributes (for Google, the "email" attribute is typically provided)
        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found in OAuth2 user attributes");
        }

        // If you want to restrict login to specific emails, check against the allowedEmails list
        if (!allowedEmails.isEmpty() && !allowedEmails.contains(email)) {
            throw new OAuth2AuthenticationException("Email not allowed: " + email);
        }

        // Check if the user already exists in the database; if not, create a new user
        UserEntity userEntity = userRepository.findByUsername(email).orElse(null);
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setUsername(email);
            // No password needed for OAuth2 login; leave it empty
            userEntity.setPassword("");
            userEntity.setRole("ROLE_USER");
            userRepository.save(userEntity);
        }

        // Return the OAuth2User object as is. Optionally, wrap it in a custom principal if needed.
        return oAuth2User;
    }
}
