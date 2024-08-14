package com.shair13.external_service.dto;

import com.shair13.external_service.model.Movie;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedMovie {
    private List<Movie> movies;
    private int pageNumber;
    private int pageSize;
}
