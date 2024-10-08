package com.shair13.external_service.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Movie {
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String director;
    private String description;
    @Min(1)
    @Max(10)
    private Double rate;
}
