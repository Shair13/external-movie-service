package com.shair13.external_service.controller;

import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.model.Movie;
import com.shair13.external_service.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody @Valid Movie movie) {
        Movie result = movieService.save(movie);
        return  ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        Movie result = movieService.getMovieById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<PagedMovie> getMovies(@RequestParam(defaultValue = "0") @Min(0) int page,
                                @RequestParam(defaultValue = "10") @Min(1) int size,
                                @RequestParam(defaultValue = "id") @Pattern(regexp = "id|title|director|rate") String sortBy) {
        PagedMovie result = movieService.getMovies(page, size, sortBy);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody @Valid Movie movie) {
        Movie result = movieService.updateMovie(id, movie);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}