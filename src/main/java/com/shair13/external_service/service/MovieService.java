package com.shair13.external_service.service;

import com.shair13.external_service.client.MovieRestClient;
import com.shair13.external_service.dto.*;
import com.shair13.external_service.mapper.MovieMapper;
import com.shair13.external_service.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRestClient movieRestClient;
    private final MovieMapper movieMapper;

    public ReadMovieDto save(WriteMovieDto writeMovie) {
        Movie movie = movieRestClient.save(movieMapper.toDomain(writeMovie));
        return movieMapper.toReadDto(movie);
    }

    public ReadMovieDto getMovieById(long id) {
        Movie movie = movieRestClient.getById(id);
        return movieMapper.toReadDto(movie);
    }

    public PagedMovie searchMovies(MovieSearchParams searchParams, PageDetails pageParams) {
        return movieRestClient.search(searchParams, pageParams);
    }

    public ReadMovieDto updateMovie(long id, WriteMovieDto writeMovie) {
        Movie movie = movieRestClient.update(id, movieMapper.toDomain(writeMovie));
        return movieMapper.toReadDto(movie);
    }

    public void deleteMovie(long id) {
        movieRestClient.delete(id);
    }
}
