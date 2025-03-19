package com.davanddev.uppgift_6.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom error controller that handles errors across the application.
 * <p>
 * Returns a JSON response with an appropriate HTTP status code and error message.
 * </p>
 */
@RestController
public class CustomErrorController implements ErrorController {

    /**
     * Handles errors by reading the HTTP status code from the request attributes and returning
     * a JSON response with an error message.
     *
     * @param request the HttpServletRequest that caused the error
     * @return a ResponseEntity containing the error message and the HTTP status code
     */
    @RequestMapping("/error")
    public ResponseEntity<Map<String, String>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Map<String, String> response = new HashMap<>();

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            httpStatus = HttpStatus.valueOf(statusCode);
            switch (statusCode) {
                case 404:
                    response.put("error", "Resource not found");
                    break;
                case 401:
                    response.put("error", "Unauthorized");
                    break;
                case 403:
                    response.put("error", "Forbidden");
                    break;
                default:
                    response.put("error", "An unexpected error occurred");
                    break;
            }
        } else {
            response.put("error", "An unexpected error occurred");
        }
        return ResponseEntity.status(httpStatus).body(response);
    }
}
