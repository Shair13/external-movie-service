package com.shair13.external_service.controller;

import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.model.Movie;
import com.shair13.external_service.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/movies")
    public Movie addMovie(@RequestBody @Valid Movie movie) {
        return movieService.save(movie);
    }

    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/movies")
    public PagedMovie getMovies(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return movieService.getMovies(page, size, sortBy);
    }

    @PutMapping("/movies/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody @Valid Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
