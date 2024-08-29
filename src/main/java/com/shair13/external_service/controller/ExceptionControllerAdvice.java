package com.shair13.external_service.controller;

import com.shair13.external_service.exception.MovieNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MovieNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleMovieNotFound(MovieNotFoundException e, HttpServletRequest request) {
        Map<String, Object> response = getErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    private Map<String, Object> getErrorResponse(String errorMessage, HttpStatus status, String path) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", errorMessage);
        response.put("path", path);
        return response;
    }
}
