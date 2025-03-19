package com.davanddev.uppgift_6.repository;

import com.davanddev.uppgift_6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for the {@link User} entity.
 * <p>
 * Provides CRUD operations and a custom query method to find a user by email.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for.
     * @return an {@link Optional} containing the found user, or empty if no user was found.
     */
    Optional<User> findByEmail(String email);
}
