package com.shair13.external_service.client;

import com.shair13.external_service.dto.MovieSearchParams;
import com.shair13.external_service.dto.PageDetails;
import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.exception.MovieNotFoundException;
import com.shair13.external_service.model.Movie;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class MovieTemplateImpl implements MovieClient {

    private final String MOVIES_URL = "/movies";

    private final RestTemplate restTemplate;

    @Override
    public Movie save(Movie movie) {
        HttpEntity<Movie> requestEntity = new HttpEntity<>(movie);

        ResponseEntity<Movie> response;

        try {
            response = restTemplate.exchange(
                    MOVIES_URL,
                    HttpMethod.POST,
                    requestEntity,
                    Movie.class
            );
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
        return response.getBody();
    }

    @Override
    public Movie getById(long id) {
        String url = MOVIES_URL + "/{id}";

        ResponseEntity<Movie> response;

        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    Movie.class,
                    id
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new MovieNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
        return response.getBody();
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

        ResponseEntity<PagedMovie> response;

        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    PagedMovie.class
            );
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
        return response.getBody();
    }

    @Override
    public Movie update(long id, Movie movie) {
        String url = MOVIES_URL + "/{id}";

        HttpEntity<Movie> requestEntity = new HttpEntity<>(movie);

        ResponseEntity<Movie> response;

        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    Movie.class,
                    id
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new MovieNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
        return response.getBody();
    }

    @Override
    public void delete(long id) {
        String url = MOVIES_URL + "/{id}";

        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    null,
                    Void.class,
                    id
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new MovieNotFoundException(id);
        } catch (HttpServerErrorException e) {
            throw new InternalServerErrorException("Server Error");
        }
    }
}
