package com.shair13.external_service.controller;

import com.shair13.external_service.dto.MoviePageParams;
import com.shair13.external_service.dto.MovieSearchParams;
import com.shair13.external_service.dto.PagedMovie;
import com.shair13.external_service.model.Movie;
import com.shair13.external_service.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Movies")
@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Add a new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    ResponseEntity<Movie> addMovie(@RequestBody @Valid Movie movie) {
        Movie result = movieService.save(movie);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Operation(summary = "Get movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie Received",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id value",
                    content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<Movie> getMovie(
            @PathVariable
            @Parameter(description = "Id of wanted movie", required = true) Long id
    ) {
        Movie result = movieService.getMovieById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "search movies by title, director and rate. Get in pages ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies received",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedMovie.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or searching values",
                    content = @Content)
    })
    @GetMapping
    ResponseEntity<PagedMovie> findMovies(
            @RequestParam(required = false) @Min(0)
            @Parameter(description = "Page number, minimum is 0") Integer page,

            @RequestParam(required = false) @Min(1)
            @Parameter(description = "Page size, minimum is 1") Integer size,

            @RequestParam(required = false, name = "sort-by") @Pattern(regexp = "id|title|director|description|rate")
            @Parameter(description = "Sort by field: id, title, director, description or rate") String sortBy,

            @RequestParam(required = false)
            @Parameter(description = "Search movie by title's fragment") String title,

            @RequestParam(required = false)
            @Parameter(description = "Search movie by director's fragment") String director,

            @RequestParam(required = false)
            @Parameter(description = "Search movie by description's fragment") String description,

            @RequestParam(required = false, name = "rate-gt") @Min(0) @Max(10)
            @Parameter(description = "Search movie by rate greater than") Double rateGreaterThan
    ) {
        MovieSearchParams searchParams = new MovieSearchParams(title, director, description, rateGreaterThan);
        MoviePageParams pageParams = MoviePageParams.create(page, size, sortBy);

        PagedMovie result = movieService.searchMovies(searchParams, pageParams);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    ResponseEntity<Movie> updateMovie(
            @PathVariable
            @Parameter(description = "Id of wanted movie", required = true) Long id,
            @RequestBody @Valid Movie movie
    ) {
        Movie result = movieService.updateMovie(id, movie);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Remove movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie removed",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid id value",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteMovie(
            @PathVariable
            @Parameter(description = "Id of wanted movie", required = true) Long id
    ) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}