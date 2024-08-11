package com.shair13.external_service.service;

import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final WebClient webClient;

    public Movie save(Movie movie) {
        return webClient.post()
                .uri("/movies")
                .body(Mono.just(movie), Movie.class)
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
    }

    public Movie getMovieById(Long id) {
        return webClient.get()
                .uri("/movies/{id}", id)
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
    }

    public PagedMovie getMovies(int page, int size, String sortBy) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movies")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("sortBy", sortBy)
                        .build())
                .retrieve()
                .bodyToMono(PagedMovie.class)
                .block();
    }

    public Movie updateMovie(Long id, Movie movie) {
        return webClient.put()
                .uri("/movies/{id}", id)
                .body(Mono.just(movie), Movie.class)
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
    }

    public Movie deleteMovie(Long id) {
        return webClient.delete()
                .uri("/movies/{id}", id)
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
    }
}
