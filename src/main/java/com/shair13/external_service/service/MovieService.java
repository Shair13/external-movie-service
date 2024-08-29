package com.shair13.external_service.service;

import com.shair13.external_service.client.MovieClient;
import com.shair13.external_service.dto.*;
import com.shair13.external_service.mapper.MovieMapper;
import com.shair13.external_service.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieClient movieClient;
    private final MovieMapper movieMapper;

    public ReadMovieDto save(WriteMovieDto writeMovie) {
        Movie movie = movieClient.save(movieMapper.toDomain(writeMovie));
        return movieMapper.toReadDto(movie);
    }

    public ReadMovieDto getMovieById(long id) {
        Movie movie = movieClient.getById(id);
        return movieMapper.toReadDto(movie);
    }

    public PagedMovie searchMovies(MovieSearchParams searchParams, PageDetails pageParams) {
        return movieClient.search(searchParams, pageParams);
    }

    public ReadMovieDto updateMovie(long id, WriteMovieDto writeMovie) {
        Movie movie = movieClient.update(id, movieMapper.toDomain(writeMovie));
        return movieMapper.toReadDto(movie);
    }

    public void deleteMovie(long id) {
        movieClient.delete(id);
    }
}
