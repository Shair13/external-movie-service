package com.shair13.external_service.client;

import com.shair13.external_service.dto.MovieSearchParams;
import com.shair13.external_service.dto.PageDetails;
import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.exception.MovieNotFoundException;
import com.shair13.external_service.model.Movie;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
@Primary
public class MovieClientImpl implements MovieClient {
    private final String MOVIES_URL = "/movies";
    private final RestClient restClient;


    @Override
    public Movie save(Movie movie) {
        try {
            return restClient.post()
                    .uri(MOVIES_URL)
                    .body(movie)
                    .retrieve()
                    .body(Movie.class);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server error");
        }
    }

    @Override
    public Movie getById(long id) {
        String url = MOVIES_URL + "/" + id;

        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Movie.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new MovieNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
    }

    @Override
    public PagedMovie search(MovieSearchParams searchParams, PageDetails pageParams) {
        String url = UriComponentsBuilder.fromPath(MOVIES_URL)
                .queryParam("page", pageParams.getPage())
                .queryParam("size", pageParams.getSize())
                .queryParam("sort", pageParams.getSort())
                .queryParam("title", searchParams.getTitle())
                .queryParam("director", searchParams.getDirector())
                .queryParam("description", searchParams.getDescription())
                .queryParam("rate-gt", searchParams.getRateGreaterThan())
                .build()
                .toUriString();

        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(PagedMovie.class);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server error");
        }
    }

    @Override
    public Movie update(long id, Movie movie) {
        String url = MOVIES_URL + "/" + id;

        try {
            return restClient.put()
                    .uri(url)
                    .body(movie)
                    .retrieve()
                    .body(Movie.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new MovieNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
    }

    @Override
    public void delete(long id) {
        String url = MOVIES_URL + "/" + id;

        try {
            restClient.delete()
                    .uri(url)
                    .retrieve();
        } catch (HttpClientErrorException.NotFound e) {
            throw new MovieNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
    }
}
