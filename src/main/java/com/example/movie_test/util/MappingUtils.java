package com.example.movie_test.util;

import com.example.movie_test.dto.MovieDto;
import com.example.movie_test.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MappingUtils {
    public MovieDto movieToDto(Movie movie) {
        return new MovieDto(movie.getName(), movie.getDescription(), movie.getType(),
                movie.getGenre(), movie.getReleaseDate());
    }

    public Movie dtoToMovie(MovieDto dto) {
        return new Movie(dto.getName(), dto.getDescription(), dto.getType(),
                dto.getGenre(), dto.getReleaseDate());
    }
}