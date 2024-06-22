package com.example.movie_test.service;

import com.example.movie_test.dto.FilmType;
import com.example.movie_test.dto.MovieDto;
import com.example.movie_test.entity.Movie;
import com.example.movie_test.exception.ErrorInputData;
import com.example.movie_test.exception.MovieNotFoundException;
import com.example.movie_test.repository.MovieRepository;
import com.example.movie_test.util.MappingUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.movie_test.dto.FilmType.SHORT_FILM;

public class MovieServiceImplTest {
    MovieServiceImpl sut;

    MovieRepository repository = Mockito.mock(MovieRepository.class);
    MappingUtils mapper = Mockito.mock(MappingUtils.class);

    @BeforeEach
    public void init() {
        sut = new MovieServiceImpl(repository, mapper);
    }

    @AfterEach
    public void afterEach() {
        sut = null;
    }

    @Test
    public void addOneFilmTest_200ok() {
        // given
        Movie movie = new Movie(1, "TestFilm", "testDescription", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));
        MovieDto movieDto = new MovieDto("TestFilm", "testDescription", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));

        Mockito.when(mapper.dtoToMovie(movieDto)).thenReturn(movie);
        Mockito.when(repository.findByNameAndReleaseDate(movie.getName(), movie.getReleaseDate())).thenReturn(Optional.empty());
        Mockito.when(mapper.movieToDto(movie)).thenReturn(movieDto);
        //when
        MovieDto result = sut.addOneFilm(movieDto);

        //then
        Assertions.assertEquals("name: TestFilm, description: testDescription, type: SHORT_FILM, genre: comedy," +
                " release date: 2024-06-21", result.toString());
    }

    @Test
    public void addOneFilmTest_400er() {
        // given
        Movie movie = new Movie(1, "TestFilm", "testDescription", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));
        MovieDto movieDto = new MovieDto("TestFilm", "testDescription", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));

        Mockito.when(repository.findByNameAndReleaseDate(movie.getName(), movie.getReleaseDate())).thenReturn(Optional.of(movie));
        Mockito.when(mapper.dtoToMovie(movieDto)).thenReturn(movie);
        Mockito.when(mapper.movieToDto(movie)).thenReturn(movieDto);
        //when

        //then
        ErrorInputData exception = Assertions.assertThrows(ErrorInputData.class, () -> {
            sut.addOneFilm(movieDto);
        });
        Assertions.assertEquals("Film name: TestFilm, release date: 2024-06-21 already exist in DB", exception.getMessage());
    }

    @Test
    public void addManyFilmsTest_200() {
        //given
        Movie m1 = new Movie(1, "TestFilm1", "some test description1", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 19));
        Movie m2 = new Movie(2, "TestFilm2", "some test description2", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 20));
        Movie m3 = new Movie(3, "TestFilm3", "some test description3", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));

        MovieDto mDto1 = new MovieDto("TestFilm1", "some test description1", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 19));
        MovieDto mDto2 = new MovieDto("TestFilm2", "some test description2", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 20));
        MovieDto mDto3 = new MovieDto("TestFilm3", "some test description3", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));

        List<MovieDto> movies = Arrays.asList(mDto1, mDto2, mDto3);

        Mockito.when(mapper.dtoToMovie(mDto1)).thenReturn(m1);
        Mockito.when(mapper.movieToDto(m1)).thenReturn(mDto1);
        Mockito.when(repository.findByNameAndReleaseDate(m1.getName(), m1.getReleaseDate())).thenReturn(Optional.empty());

        Mockito.when(mapper.dtoToMovie(mDto2)).thenReturn(m2);
        Mockito.when(mapper.movieToDto(m2)).thenReturn(mDto2);
        Mockito.when(repository.findByNameAndReleaseDate(m2.getName(), m2.getReleaseDate())).thenReturn(Optional.empty());

        Mockito.when(mapper.dtoToMovie(mDto3)).thenReturn(m3);
        Mockito.when(mapper.movieToDto(m3)).thenReturn(mDto3);
        Mockito.when(repository.findByNameAndReleaseDate(m3.getName(), m3.getReleaseDate())).thenReturn(Optional.empty());

        //when
        List<MovieDto> result = sut.addManyFilms(movies);

        //then
        Assertions.assertEquals("[name: TestFilm1, description: some test description1, type: SHORT_FILM, genre: comedy, release" +
                " date: 2024-06-19, name: TestFilm2, description: some test description2, type: SHORT_FILM, genre: comedy," +
                " release date: 2024-06-20, name: TestFilm3, description: some test description3, type: SHORT_FILM, genre: comedy, " +
                "release date: 2024-06-21]", result.toString());
    }

    @Test
    public void addManyFilmsTest_200ok_partlySave() {
        //given
        MovieDto mDto1 = new MovieDto("TestFilm1", "some test description1", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 19));
        MovieDto mDto2 = new MovieDto("TestFilm2", "some test description2", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 20));
        MovieDto mDto3 = new MovieDto("TestFilm3", "some test description3", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));
        List<MovieDto> movies = Arrays.asList(mDto1, mDto2, mDto3);

        Movie m1 = new Movie(1, "TestFilm1", "some test description1", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 19));
        Movie m2 = new Movie(2, "TestFilm2", "some test description2", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 20));
        Movie m3 = new Movie(3, "TestFilm3", "some test description3", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));

        Mockito.when(mapper.dtoToMovie(mDto1)).thenReturn(m1);
        Mockito.when(mapper.movieToDto(m1)).thenReturn(mDto1);
        Mockito.when(repository.findByNameAndReleaseDate(m1.getName(), m1.getReleaseDate())).thenReturn(Optional.empty());

        Mockito.when(mapper.dtoToMovie(mDto2)).thenReturn(m2);
        Mockito.when(mapper.movieToDto(m2)).thenReturn(mDto2);
        Mockito.when(repository.findByNameAndReleaseDate(m2.getName(), m2.getReleaseDate())).thenReturn(Optional.of(m2));

        Mockito.when(mapper.dtoToMovie(mDto3)).thenReturn(m3);
        Mockito.when(mapper.movieToDto(m3)).thenReturn(mDto3);
        Mockito.when(repository.findByNameAndReleaseDate(m3.getName(), m3.getReleaseDate())).thenReturn(Optional.of(m3));

        Mockito.when(mapper.movieToDto(m1)).thenReturn(new MovieDto("TestFilm1", "some test description1", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 19)));

        //when
        List<MovieDto> result = sut.addManyFilms(movies);

        //then
        Assertions.assertEquals("[name: TestFilm1, description: some test description1, type: SHORT_FILM, " +
                "genre: comedy, release date: 2024-06-19]", result.toString());
    }

    @Test
    public void addManyFilmsTest_400er() {
        //given
        MovieDto mDto1 = new MovieDto("TestFilm1", "some test description1", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 19));
        MovieDto mDto2 = new MovieDto("TestFilm2", "some test description2", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 20));
        MovieDto mDto3 = new MovieDto("TestFilm3", "some test description3", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));
        List<MovieDto> movies = Arrays.asList(mDto1, mDto2, mDto3);

        Movie m1 = new Movie(1, "TestFilm1", "some test description1", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 19));
        Movie m2 = new Movie(2, "TestFilm2", "some test description2", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 20));
        Movie m3 = new Movie(3, "TestFilm3", "some test description3", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 21));

        Mockito.when(mapper.dtoToMovie(mDto1)).thenReturn(m1);
        Mockito.when(mapper.movieToDto(m1)).thenReturn(mDto1);
        Mockito.when(repository.findByNameAndReleaseDate(m1.getName(), m1.getReleaseDate())).thenReturn(Optional.of(m1));

        Mockito.when(mapper.dtoToMovie(mDto2)).thenReturn(m2);
        Mockito.when(mapper.movieToDto(m2)).thenReturn(mDto2);
        Mockito.when(repository.findByNameAndReleaseDate(m2.getName(), m2.getReleaseDate())).thenReturn(Optional.of(m2));

        Mockito.when(mapper.dtoToMovie(mDto3)).thenReturn(m3);
        Mockito.when(mapper.movieToDto(m3)).thenReturn(mDto3);
        Mockito.when(repository.findByNameAndReleaseDate(m3.getName(), m3.getReleaseDate())).thenReturn(Optional.of(m3));
        //when

        //then
        ErrorInputData exception = Assertions.assertThrows(ErrorInputData.class, () -> {
            sut.addManyFilms(movies);
        });
        Assertions.assertEquals("Films [name: TestFilm1, description: some test description1, type: SHORT_FILM," +
                " genre: comedy, release date: 2024-06-19, name: TestFilm2, description: some test description2, " +
                "type: SHORT_FILM, genre: comedy, release date: 2024-06-20, name: TestFilm3, description: some test description3, type: SHORT_FILM," +
                " genre: comedy, release date: 2024-06-21] already exist in DB", exception.getMessage());
    }

    @Test
    public void getByNameTest_200ok() {
        //given
        String name = "Common name";
        Movie m1 = new Movie(1, "Common name", "some test description1", SHORT_FILM, "comedy",
                LocalDate.of(2023, 6, 19));
        Movie m2 = new Movie(2, "Common name", "some test description2", SHORT_FILM, "comedy", LocalDate.of(2024, 6, 20));
        List<Movie> movieList = Arrays.asList(m1, m2);
        Mockito.when(repository.findByName(name)).thenReturn(movieList);

        //when
        List<Movie> result = sut.getByName(name);

        //then
        Assertions.assertEquals("[name: Common name, release date: 2023-06-19, name: Common name, release date: 2024-06-20]",
                result.toString());
    }

    @Test
    public void getByNameTest_400er() {
        //given
        String name = "name";
        Mockito.when(repository.findByName(name)).thenThrow(new MovieNotFoundException("Film with name " +
                name + " not found"));

        //when
        MovieNotFoundException exception = Assertions.assertThrows(MovieNotFoundException.class, () -> {
            sut.getByName(name);
        });

        //then
        Assertions.assertEquals("Film with name name not found", exception.getMessage());
    }

    @Test
    public void getByTypeTest_200ok() {
        // given
        FilmType type = SHORT_FILM;
        Movie m1 = new Movie(1, "filmName1", "some test description1", SHORT_FILM, "comedy",
                LocalDate.of(2023, 6, 19));
        Movie m2 = new Movie(2, "filmName2", "some test description2", SHORT_FILM, "comedy",
                LocalDate.of(2024, 6, 20));
        List<Movie> movieList = Arrays.asList(m1, m2);
        Mockito.when(repository.findByType(type)).thenReturn(movieList);

        //when
        List<Movie> result = sut.getByType(type);

        //then
        Assertions.assertEquals("[name: filmName1, release date: 2023-06-19, name: filmName2, release date: 2024-06-20]",
                result.toString());
    }

    @Test
    public void getByTypeTest_400er() {
        // given
        FilmType type = SHORT_FILM;
        Mockito.when(repository.findByType(type)).thenThrow(new MovieNotFoundException("Film type " +
                type + " not found"));

        //when

        //then
        MovieNotFoundException exception = Assertions.assertThrows(MovieNotFoundException.class, () -> {
            sut.getByType(type);
        });
        Assertions.assertEquals("Film type SHORT_FILM not found",
                exception.getMessage());
    }

    @Test
    public void getByDateTest_200ok() {
        // given
        String date = "2024";
        Movie m1 = new Movie(1, "filmName1", "some test description1", SHORT_FILM, "comedy",
                LocalDate.of(2024, 6, 19));
        Movie m2 = new Movie(2, "filmName2", "some test description2", SHORT_FILM, "comedy",
                LocalDate.of(2024, 6, 20));
        List<Movie> movieList = Arrays.asList(m1, m2);
        Mockito.when(repository.findByDate(date)).thenReturn(movieList);

        // when
        List<Movie> result = sut.getByDate(date);

        // then
        Assertions.assertEquals("[name: filmName1, release date: 2024-06-19, name: filmName2, release date: " +
                "2024-06-20]", result.toString());
    }

    @Test
    public void getByDateTest_400er() {
        // given
        String date = "2024";
        Mockito.when(repository.findByDate(date)).thenReturn(new ArrayList<>());
        // when

        // then
        MovieNotFoundException exception = Assertions.assertThrows(MovieNotFoundException.class, () -> {
            sut.getByDate(date);
        });
        Assertions.assertEquals("Film with release date 2024 not found", exception.getMessage());
    }
}