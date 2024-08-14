package com.shair13.external_service.service;

import com.shair13.external_service.client.MovieRestClient;
import com.shair13.external_service.dto.*;
import com.shair13.external_service.mapper.MovieMapper;
import com.shair13.external_service.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private final Long ID = 1L;
    private final String TITLE = "New Hope";
    private final String DIRECTOR = "George Lucas";
    private final String DESCRIPTION = "Some description";
    private final Double RATE = 9.7;

    @Mock
    private MovieRestClient mockMovieRestClient;
    @Mock
    private MovieMapper mockMovieMapper;
    @InjectMocks
    private MovieService movieService;

    @Test
    void shouldSaveMovie() {
        // given
        WriteMovieDto writeMovie = new WriteMovieDto(TITLE, DIRECTOR, DESCRIPTION, RATE);
        Movie movie = new Movie(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovie = new ReadMovieDto(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);

        when(mockMovieMapper.toDomain(writeMovie)).thenReturn(movie);
        when(mockMovieRestClient.save(movie)).thenReturn(movie);
        when(mockMovieMapper.toReadDto(movie)).thenReturn(readMovie);

        // when
        ReadMovieDto result = movieService.save(writeMovie);

        // then
        assertEquals(readMovie, result);
    }

    @Test
    void getMovieById() {
        // given
        Movie movie = new Movie(ID, TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovie = new ReadMovieDto(ID, TITLE, DIRECTOR, DESCRIPTION, RATE);

        when(mockMovieRestClient.getById(ID)).thenReturn(movie);
        when(mockMovieMapper.toReadDto(movie)).thenReturn(readMovie);

        // when
        ReadMovieDto result = movieService.getMovieById(ID);

        // then
        assertEquals(readMovie, result);
    }

    @Test
    void searchMovies() {
        // given
        MovieSearchParams searchParams = new MovieSearchParams(TITLE, DIRECTOR, DESCRIPTION, RATE);
        PageDetails pageParams = PageDetails.create(0, 10, "id");

        ReadMovieDto readMovieOne = new ReadMovieDto(ID, TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovieTwo = new ReadMovieDto(ID + 1, "Inception", "Christopher Nolan", "Another Description", 8.0);
        List<ReadMovieDto> readMovies = List.of(readMovieOne, readMovieTwo);

        PagedMovie pagedMovie = new PagedMovie(readMovies, pageParams);

        when(mockMovieRestClient.search(searchParams, pageParams)).thenReturn(pagedMovie);

        // when
        PagedMovie result = movieService.searchMovies(searchParams, pageParams);

        // then
        assertEquals(pagedMovie, result);

    }

    @Test
    void updateMovie() {
        // given
        WriteMovieDto writeMovie = new WriteMovieDto(TITLE, DIRECTOR, DESCRIPTION, RATE);
        Movie movie = new Movie(ID, TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovie = new ReadMovieDto(ID, TITLE, DIRECTOR, DESCRIPTION, RATE);

        when(mockMovieMapper.toDomain(writeMovie)).thenReturn(movie);
        when(mockMovieRestClient.update(ID, movie)).thenReturn(movie);
        when(mockMovieMapper.toReadDto(movie)).thenReturn(readMovie);

        // then
        ReadMovieDto result = movieService.updateMovie(ID, writeMovie);

        // then
        assertEquals(readMovie, result);
    }

    @Test
    void deleteMovie() {
        // when
        movieService.deleteMovie(ID);

        // then
        verify(mockMovieRestClient, times(1)).delete(ID);
    }
}