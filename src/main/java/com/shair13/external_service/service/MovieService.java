package com.shair13.external_service.service;

import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.exception.MovieNotFoundException;
import com.shair13.external_service.model.Movie;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
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
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }

    public Movie getMovieById(Long id) {
        return webClient.get()
                .uri("/movies/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new MovieNotFoundException(id);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }

    public PagedMovie getMovies(int page, int size, String sortBy) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movies")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("sort-by", sortBy)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new InternalServerErrorException("Bad Request");
                })
                .bodyToMono(PagedMovie.class)
                .block();
    }

    public PagedMovie searchMovies(int page, int size, String sortBy, String title, String director, Double rate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movies/search")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("sort-by", sortBy)
                        .queryParam("title", title)
                        .queryParam("director", director)
                        .queryParam("rate-gt", rate)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new InternalServerErrorException("Bad Request");
                })
                .bodyToMono(PagedMovie.class)
                .block();
    }

    public Movie updateMovie(Long id, Movie movie) {
        return webClient.put()
                .uri("/movies/{id}", id)
                .body(Mono.just(movie), Movie.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new MovieNotFoundException(id);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }

    public void deleteMovie(Long id) {
        webClient.delete()
                .uri("/movies/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    throw new MovieNotFoundException(id);
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new InternalServerErrorException("Server Error");
                })
                .bodyToMono(Movie.class)
                .block();
    }
}
