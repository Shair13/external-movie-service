package com.shair13.external_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDetails {
    Integer page;
    Integer size;
    String sort;

    public static PageDetails create(Integer page, Integer size, String sort) {
        return new PageDetails(
                page != null ? page : 0,
                size != null ? size : 10,
                sort != null ? sort : "id"
        );
    }
}
