package com.davanddev.uppgift_6.controller;

import com.davanddev.uppgift_6.dto.AuthenticationRequest;
import com.davanddev.uppgift_6.dto.AuthenticationResponse;
import com.davanddev.uppgift_6.dto.RegisterRequest;
import com.davanddev.uppgift_6.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller for handling authentication endpoints.
 */
@Tag(name = "Authentication", description = "Operations related to user authentication")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    // Rate limiting configuration
    private static final int RATE_LIMIT_MAX_CALLS = 3;
    private static final long RATE_LIMIT_WINDOW_MILLIS = 60_000; // 1 minute
    private static final long RATE_LIMIT_BLOCK_DURATION_MILLIS = 5 * 60_000; // 5 minutes

    // Failed login configuration
    private static final int MAX_FAILED_LOGIN_ATTEMPTS = 3;
    private static final long FAILED_LOGIN_BLOCK_DURATION_MILLIS = 15 * 60_000;

    private static final Map<String, RateLimitInfo> rateLimitMap = new ConcurrentHashMap<>();
    private static final Map<String, FailedLoginInfo> failedLoginMap = new ConcurrentHashMap<>();

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
        String clientIp = servletRequest.getRemoteAddr();
        if (isRateLimited(clientIp)) {
            return ResponseEntity.status(429)
                    .body(Map.of("error", "Rate limit exceeded. Try again later."));
        }
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates a user with email and password.
     *
     * @param request        the authentication request
     * @param servletRequest the HTTP request
     * @return a JSON message with a JWT token and a success message if credentials are valid; otherwise, error message
     */
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token along with a welcome message")
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request,
                                          HttpServletRequest servletRequest) {
        String clientIp = servletRequest.getRemoteAddr();
        if (isRateLimited(clientIp) || isFailedLoginBlocked(clientIp)) {
            return ResponseEntity.status(429)
                    .body(Map.of("error", "Too many attempts. Try again later."));
        }
        try {
            AuthenticationResponse authResponse = authService.authenticate(request);
            clearFailedLogin(clientIp);

            String msg = "OK, " + request.getEmail() + " du är inloggad";
            Map<String, String> responseBody = Map.of("message", msg, "token", authResponse.getToken());

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            incrementFailedLogin(clientIp);
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
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
        // Here you could also implement token revocation if needed.
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

    // Private helper methods for rate limiting and failed login tracking

    private boolean isRateLimited(String ip) {
        RateLimitInfo info = rateLimitMap.computeIfAbsent(ip, k -> new RateLimitInfo());
        long now = System.currentTimeMillis();
        synchronized (info) {
            if (now < info.blockedUntil) {
                return true;
            }
            if (now - info.windowStart >= RATE_LIMIT_WINDOW_MILLIS) {
                info.windowStart = now;
                info.count = 0;
            }
            info.count++;
            if (info.count > RATE_LIMIT_MAX_CALLS) {
                info.blockedUntil = now + RATE_LIMIT_BLOCK_DURATION_MILLIS;
                return true;
            }
            return false;
        }
    }

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
     * Inner class to track rate limit info.
     */
    private static class RateLimitInfo {
        long windowStart = System.currentTimeMillis();
        int count = 0;
        long blockedUntil = 0L;
    }

    /**
     * Inner class to track failed login attempts.
     */
    private static class FailedLoginInfo {
        int failedAttempts = 0;
        long blockedUntil = 0L;
    }
}
