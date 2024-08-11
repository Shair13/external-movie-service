package com.shair13.external_service.model;

import lombok.Data;

@Data
public class Movie {
    private Long id;
    private String title;
    private String director;
    private String description;
    private double rate;
}
