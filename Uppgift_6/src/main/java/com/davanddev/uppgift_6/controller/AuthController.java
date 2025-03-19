package com.davanddev.uppgift_6.controller;

import com.davanddev.uppgift_6.dto.AuthenticationRequest;
import com.davanddev.uppgift_6.dto.AuthenticationResponse;
import com.davanddev.uppgift_6.dto.RegisterRequest;
import com.davanddev.uppgift_6.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling authentication endpoints.
 */
@Tag(name = "Authentication", description = "Operations related to user authentication")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    // Failed login configuration
    private static final int MAX_FAILED_LOGIN_ATTEMPTS = 3;
    private static final long FAILED_LOGIN_BLOCK_DURATION_MILLIS = 15 * 60_000; // 15 minuter

    private static final Map<String, FailedLoginInfo> failedLoginMap = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * Registers a new user.
     *
     * @param request        the registration request
     * @param servletRequest the HTTP request
     * @return the authentication response containing the JWT token
     */
    @Operation(summary = "Register new user", description = "Creates a new user account and returns a JWT token")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates a user with email and password, sets JWT token in a cookie,
     * and automatically redirects to /dashboard.
     *
     * @param request         the authentication request containing credentials.
     * @param servletRequest  the HTTP request.
     * @param servletResponse the HTTP response.
     */
    @Operation(summary = "User login", description = "Authenticates a user, sets a JWT token in a cookie, and redirects to /dashboard")
    @PostMapping("/login")
    public void authenticate(@RequestBody AuthenticationRequest request,
                             HttpServletRequest servletRequest,
                             HttpServletResponse servletResponse) throws IOException {
        String clientIp = servletRequest.getRemoteAddr();
        if (isFailedLoginBlocked(clientIp)) {
            servletResponse.setStatus(429);
            servletResponse.getWriter().write("{\"error\": \"Too many attempts. Try again later.\"}");
            return;
        }
        try {
            AuthenticationResponse authResponse = authService.authenticate(request);
            clearFailedLogin(clientIp);

            // Puts the JWT token in a cookie
            Cookie tokenCookie = new Cookie("jwt_token", authResponse.getToken());
            tokenCookie.setHttpOnly(true);
            tokenCookie.setPath("/");
            // Expire the cookie in 24 hours
            tokenCookie.setMaxAge(86400);
            servletResponse.addCookie(tokenCookie);

            // Redirects to /dashboard
            servletResponse.sendRedirect("/dashboard");
        } catch (Exception e) {
            incrementFailedLogin(clientIp);
            servletResponse.setStatus(401);
            servletResponse.getWriter().write("{\"error\": \"Invalid credentials\"}");
        }
    }

    /**
     * Logs out a user.
     *
     * @return a confirmation message
     */
    @Operation(summary = "User logout", description = "Logs out the user. This endpoint requires a valid JWT token.")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "You are now logged out"));
    }

    /**
     * Handles successful OAuth2 authentication.
     *
     * @param oauth2User the authenticated OAuth2 user
     * @return a map containing the user's email, name, and JWT token
     */
    @Operation(summary = "OAuth2 login success", description = "Handles OAuth2 login success and returns a JWT token along with user info")
    @GetMapping("/oauth2/success")
    public ResponseEntity<Map<String, String>> handleOAuth2Success(@AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            Map<String, String> response = new HashMap<>();
            response.put("email", oauth2User.getAttribute("email"));
            response.put("name", oauth2User.getAttribute("name"));
            response.put("token", authService.handleOAuth2User(oauth2User));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(Map.of("error", "No OAuth2 user found"));
    }

    /**
     * Handles OAuth2 authentication failure.
     *
     * @return a map containing an error message
     */
    @Operation(summary = "OAuth2 login failure", description = "Handles OAuth2 login failure")
    @GetMapping("/oauth2/failure")
    public ResponseEntity<Map<String, String>> handleOAuth2Failure() {
        return ResponseEntity.badRequest().body(Map.of("error", "Authentication failed"));
    }

    // Private helper methods for failed login tracking

    private boolean isFailedLoginBlocked(String ip) {
        FailedLoginInfo info = failedLoginMap.get(ip);
        if (info == null) {
            return false;
        }
        long now = System.currentTimeMillis();
        return now < info.blockedUntil;
    }

    private void incrementFailedLogin(String ip) {
        FailedLoginInfo info = failedLoginMap.computeIfAbsent(ip, k -> new FailedLoginInfo());
        long now = System.currentTimeMillis();
        synchronized (info) {
            if (info.blockedUntil != 0 && now > info.blockedUntil) {
                info.failedAttempts = 0;
                info.blockedUntil = 0;
            }
            info.failedAttempts++;
            if (info.failedAttempts >= MAX_FAILED_LOGIN_ATTEMPTS) {
                info.blockedUntil = now + FAILED_LOGIN_BLOCK_DURATION_MILLIS;
            }
        }
    }

    private void clearFailedLogin(String ip) {
        failedLoginMap.remove(ip);
    }

    /**
     * Inner class to track failed login attempts.
     */
    private static class FailedLoginInfo {
        int failedAttempts = 0;
        long blockedUntil = 0L;
    }
}
