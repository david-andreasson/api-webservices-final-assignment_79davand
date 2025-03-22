package com.davanddev.uppgift_6.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Filter that applies rate limiting to specific endpoints.
 * Limits requests for "/auth/register" and "/login" to a maximum number per minute.
 */
@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 3;
    private static final long BLOCK_TIME_MILLIS = 5 * 60_000; // 5 minutes
    private static final long TIME_WINDOW_MILLIS = 60_000;    // 1 minute

    private final Map<String, RateLimitInfo> ipRequests = new ConcurrentHashMap<>();

    /**
     * Filters incoming requests and enforces rate limiting on specified endpoints.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the filter chain to pass control to the next filter
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        boolean isLimitedEndpoint = path.equalsIgnoreCase("/auth/register") || path.equalsIgnoreCase("/login");

        if (isLimitedEndpoint) {
            String ip = request.getRemoteAddr();
            RateLimitInfo info = ipRequests.computeIfAbsent(ip, k -> new RateLimitInfo());

            if (info.blockedUntil > System.currentTimeMillis()) {
                response.setStatus(429);
                response.getWriter().write("Too many requests - blocked for 5 minutes");
                return;
            } else {
                info.blockedUntil = 0L;
            }

            long now = System.currentTimeMillis();
            while (!info.requestTimestamps.isEmpty() &&
                    (now - info.requestTimestamps.peekFirst()) > TIME_WINDOW_MILLIS) {
                info.requestTimestamps.pollFirst();
            }

            info.requestTimestamps.addLast(now);

            if (info.requestTimestamps.size() > MAX_REQUESTS_PER_MINUTE) {
                info.blockedUntil = now + BLOCK_TIME_MILLIS;
                response.setStatus(429);
                response.getWriter().write("Too many requests - you are blocked for 5 minutes");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Helper class to store rate limiting information for a specific IP address.
     */
    private static class RateLimitInfo {
        Deque<Long> requestTimestamps = new LinkedList<>();
        long blockedUntil = 0L;
    }
}
