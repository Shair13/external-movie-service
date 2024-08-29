package com.shair13.external_service.client;

import com.shair13.external_service.dto.MovieSearchParams;
import com.shair13.external_service.dto.PageDetails;
import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.exception.MovieNotFoundException;
import com.shair13.external_service.model.Movie;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class MovieWebClientImpl implements MovieClient {

    private final WebClient webClient;

    @Override
    public Movie save(Movie movie) {
        return webClient.post()
                .uri("/movies")
                .body(Mono.just(movie), Movie.class)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }

    @Override
    public Movie getById(long id) {
        return webClient.get()
                .uri("/movies/{id}", id)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NOT_FOUND, clientResponse -> {
                    throw new MovieNotFoundException(id);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }

    @Override
    public PagedMovie search(MovieSearchParams searchParams, PageDetails pageParams) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movies")
                        .queryParam("page", pageParams.getPage())
                        .queryParam("size", pageParams.getSize())
                        .queryParam("sort", pageParams.getSort())
                        .queryParam("title", searchParams.getTitle())
                        .queryParam("director", searchParams.getDirector())
                        .queryParam("description", searchParams.getDescription())
                        .queryParam("rate-gt", searchParams.getRateGreaterThan())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(PagedMovie.class)
                .block();
    }

    @Override
    public Movie update(long id, Movie movie) {
        return webClient.put()
                .uri("/movies/{id}", id)
                .body(Mono.just(movie), Movie.class)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NOT_FOUND, clientResponse -> {
                    throw new MovieNotFoundException(id);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }

    @Override
    public void delete(long id) {
        webClient.delete()
                .uri("/movies/{id}", id)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NOT_FOUND, clientResponse -> {
                    throw new MovieNotFoundException(id);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }
}