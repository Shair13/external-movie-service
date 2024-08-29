package com.shair13.external_service.client;

import com.shair13.external_service.dto.PageDetails;
import com.shair13.external_service.dto.MovieSearchParams;
import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.model.Movie;

public interface MovieClient {
    Movie save(Movie movie);
    Movie getById(long id);
    PagedMovie search(MovieSearchParams searchParams, PageDetails pageParams);
    Movie update(long id, Movie movie);
    void delete(long id);
}
