package com.example.movie_test.controller;

import com.example.movie_test.dto.ErrorResponse;
import com.example.movie_test.dto.FilmType;
import com.example.movie_test.dto.MovieDto;
import com.example.movie_test.entity.Movie;
import com.example.movie_test.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movie-api")
@Tag(name = "Фильмы", description = "методы для работы с фильмами")
public class MovieController {
    private final MovieService service;

    @Operation(summary = "Добавление одного фильма")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = MovieDto.class))}),
            @ApiResponse(responseCode = "400", description = "введен фильм, существующий в БД", content =
                    {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @PostMapping("/add-one")
    public ResponseEntity<MovieDto> addOneFilm(@RequestBody @Validated MovieDto movieDto) {
        System.out.println(movieDto);
        return new ResponseEntity<>(service.addOneFilm(movieDto), HttpStatus.OK);
    }

    @Operation(summary = "Добавление нескольких фильмов", description = "если в списке переданных фильмов есть уже сохраненный, то будут сохранены только все новые")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = MovieDto.class)))}),
            @ApiResponse(responseCode = "400", description = "все фильмы для сохранения уже есть в БД", content =
                    {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @PostMapping("/add-many")
    public ResponseEntity<List<MovieDto>> addManyFilms(@RequestBody @Validated List<MovieDto> movies) {
        return new ResponseEntity<>(service.addManyFilms(movies), HttpStatus.OK);
    }

    @Operation(summary = "Поиск фильма по названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = Movie.class)))}),
            @ApiResponse(responseCode = "404", description = "фильм не найден", content =
                    {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping("/get-by-name")
    public ResponseEntity<List<Movie>> getByName(@Parameter(description = "название фильма")
                                                 @RequestParam String name) {
        return new ResponseEntity<>(service.getByName(name), HttpStatus.OK);
    }

    @Operation(summary = "Поиск фильма по типу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Movie.class)))}),
            @ApiResponse(responseCode = "404", description = "фильм не найден", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping("/get-by-type")
    public ResponseEntity<List<Movie>> getByType(@Parameter(description = "тип фильма") @RequestParam FilmType type) {
        return new ResponseEntity<>(service.getByType(type), HttpStatus.OK);
    }

    @Operation(summary = "Поиск фильма по году выхода")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Movie.class)))}),
            @ApiResponse(responseCode = "404", description = "фильм не найден", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping("/get-by-date")
    public ResponseEntity<List<Movie>> getByYear(@RequestParam String date) {
        return new ResponseEntity<>(service.getByDate(date), HttpStatus.OK);
    }
}
