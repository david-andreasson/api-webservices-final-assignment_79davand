package com.davanddev.uppgift_6.controller;

import com.davanddev.uppgift_6.dto.AuthenticationRequest;
import com.davanddev.uppgift_6.dto.AuthenticationResponse;
import com.davanddev.uppgift_6.dto.RegisterRequest;
import com.davanddev.uppgift_6.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    // Rate limiting config
    private static final int RATE_LIMIT_MAX_CALLS = 3;
    private static final long RATE_LIMIT_WINDOW_MILLIS = 60_000; // 1 minute
    private static final long RATE_LIMIT_BLOCK_DURATION_MILLIS = 5 * 60_000; // 5 minutes

    // Failed login config
    private static final int MAX_FAILED_LOGIN_ATTEMPTS = 3;
    private static final long FAILED_LOGIN_BLOCK_DURATION_MILLIS = 15 * 60_000;

    private static final Map<String, RateLimitInfo> rateLimitMap = new ConcurrentHashMap<>();
    private static final Map<String, FailedLoginInfo> failedLoginMap = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletRequest servletRequest) {
        String clientIp = servletRequest.getRemoteAddr();
        if (isRateLimited(clientIp)) {
            return ResponseEntity.status(429)
                    .body(Map.of("error", "Rate limit exceeded. Try again later."));
        }
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request,
                                          HttpServletRequest servletRequest) {
        String clientIp = servletRequest.getRemoteAddr();
        if (isRateLimited(clientIp) || isFailedLoginBlocked(clientIp)) {
            return ResponseEntity.status(429)
                    .body(Map.of("error", "Too many attempts. Try again later."));
        }
        try {
            // Attempt authentication
            AuthenticationResponse response = authService.authenticate(request);
            // Clear failed login tracking
            clearFailedLogin(clientIp);

            // Build JSON message
            String msg = "OK, " + request.getEmail() + " du är inloggad";
            Map<String, String> responseBody = Map.of("message", msg);

            // Return 302 with Location: /dashboard
            return ResponseEntity
                    .status(302)
                    .header("Location", "/dashboard")
                    .body(responseBody);

        } catch (Exception e) {
            incrementFailedLogin(clientIp);
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

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

    @GetMapping("/oauth2/failure")
    public ResponseEntity<Map<String, String>> handleOAuth2Failure() {
        return ResponseEntity.badRequest().body(Map.of("error", "Authentication failed"));
    }

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

    private static class RateLimitInfo {
        long windowStart = System.currentTimeMillis();
        int count = 0;
        long blockedUntil = 0L;
    }

    private static class FailedLoginInfo {
        int failedAttempts = 0;
        long blockedUntil = 0L;
    }
}