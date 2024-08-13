package com.shair13.external_service.controller;

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
public class MovieController {

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
    public ResponseEntity<Movie> addMovie(@RequestBody @Valid Movie movie) {
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
    public ResponseEntity<Movie> getMovie(@PathVariable
                                          @Parameter(description = "Id of wanted movie", required = true) Long id) {
        Movie result = movieService.getMovieById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get all movies in pages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies received",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedMovie.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid pagination values",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<PagedMovie> getMovies(@RequestParam(defaultValue = "0") @Min(0)
                                                @Parameter(description = "Page number, minimum is 0") int page,

                                                @RequestParam(defaultValue = "10") @Min(1)
                                                @Parameter(description = "Page size, minimum is 1") int size,

                                                @RequestParam(defaultValue = "id", name = "sort-by") @Pattern(regexp = "id|title|director|rate")
                                                @Parameter(description = "Sort by field: id, title, director, or rate") String sortBy) {

        PagedMovie result = movieService.getMovies(page, size, sortBy);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "search movies by title, director or rate and get in pages ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies received",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagedMovie.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or searching values",
                    content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<PagedMovie> searchMovies(@RequestParam(defaultValue = "0") @Min(0)
                                                   @Parameter(description = "Page number, minimum is 0") int page,

                                                   @RequestParam(defaultValue = "10") @Min(1)
                                                   @Parameter(description = "Page size, minimum is 1") int size,

                                                   @RequestParam(defaultValue = "id", name = "sort-by") @Pattern(regexp = "id|title|director|rate")
                                                   @Parameter(description = "Sort by field: id, title, director, or rate") String sortBy,

                                                   @RequestParam(defaultValue = "")
                                                   @Parameter(description = "Search movie by title's fragment") String title,

                                                   @RequestParam(defaultValue = "")
                                                   @Parameter(description = "Search movie by director's fragment") String director,

                                                   @RequestParam(defaultValue = "0", name = "rate-gt") @Min(0) @Max(10)
                                                   @Parameter(description = "Search movie by rate greater than") Double rateGreaterThan) {

        PagedMovie result = movieService.searchMovies(page, size, sortBy, title, director, rateGreaterThan);
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
    public ResponseEntity<Movie> updateMovie(@PathVariable
                                             @Parameter(description = "Id of wanted movie", required = true) Long id,
                                             @RequestBody @Valid Movie movie) {
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
    public ResponseEntity<Void> deleteMovie(@PathVariable
                                            @Parameter(description = "Id of wanted movie", required = true) Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}