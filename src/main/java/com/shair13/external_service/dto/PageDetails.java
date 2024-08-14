package com.shair13.external_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoviePageParams {
    Integer page;
    Integer size;
    String sortBy;

    public static MoviePageParams create(Integer page, Integer size, String sortBy) {
        return new MoviePageParams(
                page != null ? page : 0,
                size != null ? size : 10,
                sortBy != null ? sortBy : "id"
        );
    }
}
