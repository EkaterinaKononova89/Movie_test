package com.example.movie_test.service;

import com.example.movie_test.dto.FilmType;
import com.example.movie_test.dto.MovieDto;
import com.example.movie_test.entity.Movie;

import java.util.List;

public interface MovieService {
    MovieDto addOneFilm(MovieDto movieDto);

    List<MovieDto> addManyFilms(List<MovieDto> movies);

    List<Movie> getByName(String name);
    List<Movie> getByType(FilmType type);

    List<Movie> getByDate(String Date);
}
