package com.shair13.external_service.client;

import com.shair13.external_service.dto.PageDetails;
import com.shair13.external_service.dto.MovieSearchParams;
import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.model.Movie;

public interface MovieRestClient {
    Movie save(Movie movie);
    Movie getById(Long id);
    PagedMovie search(MovieSearchParams searchParams, PageDetails pageParams);
    Movie update(Long id, Movie movie);
    void delete(Long id);
}
