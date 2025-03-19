package com.davanddev.uppgift_6.service;

import com.davanddev.uppgift_6.model.User;
import com.davanddev.uppgift_6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing users and for providing user details for authentication.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Loads the user details by email.
     *
     * @param email the user's email
     * @return the user details
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    /**
     * Creates or updates an OAuth2 user based on the provided OAuth2 user information.
     *
     * @param oAuth2User the OAuth2 user object
     * @return the created or updated User entity
     */
    @Transactional
    public User createOAuthUser(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        return userRepository.findByEmail(email)
                .map(user -> updateExistingUser(user, oAuth2User))
                .orElseGet(() -> createNewUser(oAuth2User));
    }

    /**
     * Updates an existing user's first and last name with data from the OAuth2 user.
     *
     * @param user       the existing user entity
     * @param oAuth2User the OAuth2 user object
     * @return the updated user entity
     */
    private User updateExistingUser(User user, OAuth2User oAuth2User) {
        user.setFirstName(oAuth2User.getAttribute("given_name"));
        user.setLastName(oAuth2User.getAttribute("family_name"));
        return userRepository.save(user);
    }

    /**
     * Creates a new user based on OAuth2 user information.
     *
     * @param oAuth2User the OAuth2 user object
     * @return the newly created user entity
     */
    private User createNewUser(OAuth2User oAuth2User) {
        return userRepository.save(User.builder()
                .email(oAuth2User.getAttribute("email"))
                .firstName(oAuth2User.getAttribute("given_name"))
                .lastName(oAuth2User.getAttribute("family_name"))
                .password(passwordEncoder.encode("")) // OAuth2 users do not require a password.
                .provider("google")
                .build());
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user's ID
     * @return the user entity
     * @throws RuntimeException if the user is not found
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Updates the first and last name of the user with the specified ID.
     *
     * @param id          the user's ID
     * @param userDetails the new user details
     * @return the updated user entity
     */
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        return userRepository.save(user);
    }

    /**
     * Deletes the user with the specified ID.
     *
     * @param id the user's ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
