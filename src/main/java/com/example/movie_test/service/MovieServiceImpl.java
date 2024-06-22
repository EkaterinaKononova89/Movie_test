package com.example.movie_test.service;

import com.example.movie_test.aspect.ToLogAdd;
import com.example.movie_test.aspect.ToLogGet;
import com.example.movie_test.dto.FilmType;
import com.example.movie_test.dto.MovieDto;
import com.example.movie_test.entity.Movie;
import com.example.movie_test.exception.ErrorInputData;
import com.example.movie_test.exception.MovieNotFoundException;
import com.example.movie_test.repository.MovieRepository;
import com.example.movie_test.util.MappingUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final MappingUtils mapper;

    public boolean isFilmExistInDb(Movie movie) {
        return Optional.of(repository.findByNameAndReleaseDate(movie.getName(), movie.getReleaseDate())).get().isPresent();
    }

    @Override
    @ToLogAdd
    public MovieDto addOneFilm(MovieDto movieDto) {
        Movie movie = mapper.dtoToMovie(movieDto);
        if (isFilmExistInDb(movie)) {
            throw new ErrorInputData("Film " + movie + " already exist in DB");
        }
        repository.save(movie);
        return movieDto;
    }

    @Override
    @ToLogAdd
    public List<MovieDto> addManyFilms(List<MovieDto> movies) {
        List<MovieDto> movieDtoListSuccess = new ArrayList<>();
        for (MovieDto m : movies) {
            Movie movie = mapper.dtoToMovie(m);
            if (!isFilmExistInDb(movie)) {
                repository.save(movie);
                movieDtoListSuccess.add(m);
            }
        }
        if (movieDtoListSuccess.isEmpty()) {
            throw new ErrorInputData("Films " + movies + " already exist in DB");
        }
        return movieDtoListSuccess;
    }

    @ToLogGet
    @Override
    public List<Movie> getByName(String name) {
        List<Movie> list = repository.findByName(name);
        if (list.isEmpty()) {
            throw new MovieNotFoundException("Film with name " + name + " not found");
        }
        return list;
    }

    @ToLogGet
    @Override
    public List<Movie> getByType(FilmType type) {
        List<Movie> list = repository.findByType(type);
        if (list.isEmpty()) {
            throw new MovieNotFoundException("Film type " + type + " not found");
        }
        return list;
    }

    @ToLogGet
    @Override
    public List<Movie> getByDate(String date) {
        List<Movie> list = repository.findByDate(date);
        if (list.isEmpty()) {
            throw new MovieNotFoundException("Film with release date " + date + " not found");
        }
        return list;
    }
}
