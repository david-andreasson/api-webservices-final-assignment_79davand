package com.davanddev.uppgift_6.controller;

import com.davanddev.uppgift_6.model.User;
import com.davanddev.uppgift_6.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 * <p>
 * Provides endpoints for retrieving all users, retrieving a user by id,
 * updating and deleting a user.
 * </p>
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints for user management operations")
public class UserController {
    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users from the system")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Retrieves a user by id.
     *
     * @param id the id of the user.
     * @return the user with the specified id.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Updates an existing user.
     *
     * @param id          the id of the user to update.
     * @param userDetails the user details to update.
     * @return the updated user.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates the information of an existing user")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    /**
     * Deletes a user by id.
     *
     * @param id the id of the user to delete.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by their unique identifier")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
