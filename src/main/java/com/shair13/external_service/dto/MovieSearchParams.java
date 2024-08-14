package com.shair13.external_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieSearchParams {
    String title;
    String director;
    String description;
    Double rateGreaterThan;
}
