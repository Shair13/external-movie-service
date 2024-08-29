package com.shair13.external_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadMovieDto {
    private Long id;
    private String title;
    private String director;
    private String description;
    private double rate;
}
