package com.example.movie_test.repository;

import com.example.movie_test.dto.FilmType;
import com.example.movie_test.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByName(String name);

    List<Movie> findByType(FilmType type);

    Optional <Movie> findByNameAndReleaseDate(String name, LocalDate date); // вспомогательный метод

    @Query(nativeQuery = true, value = "SELECT * FROM movie WHERE EXTRACT(YEAR FROM release_date) = :date")
    List <Movie> findByDate(@Param("date") String year);
}
