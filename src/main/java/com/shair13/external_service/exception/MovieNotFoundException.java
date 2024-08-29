package com.shair13.external_service.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("Movie with id = " + id + " not found");
    }
}
