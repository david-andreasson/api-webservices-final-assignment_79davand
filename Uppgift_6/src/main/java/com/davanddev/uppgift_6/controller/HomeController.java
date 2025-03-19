package com.davanddev.uppgift_6.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for handling basic endpoints such as home, login, and dashboard.
 */
@RestController
public class HomeController {

    /**
     * Home endpoint.
     *
     * @return A map containing a welcome message and a login URL.
     */
    @Operation(summary = "Home endpoint", description = "Returns a welcome message and login URL.")
    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome! Login with Google at: /oauth2/authorization/google");
        response.put("login_url", "/oauth2/authorization/google");
        return response;
    }

    /**
     * Login endpoint.
     *
     * @return A map containing a login prompt message and a login URL.
     */
    @Operation(summary = "Login endpoint", description = "Returns a prompt message with the login URL.")
    @GetMapping("/login")
    public Map<String, String> login() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Please log in using Google OAuth2");
        response.put("login_url", "/oauth2/authorization/google");
        return response;
    }

    /**
     * Dashboard endpoint (protected).
     *
     * @return A map containing a welcome message for the dashboard.
     */
    @Operation(summary = "Dashboard endpoint", description = "Returns a welcome message for the dashboard. Requires authentication.")
    @GetMapping("/dashboard")
    public Map<String, String> dashboard() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to your dashboard!");
        return response;
    }
}
