package com.example.movie_test.entity;

import com.example.movie_test.dto.FilmType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(name = "film_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FilmType type;

    @Column(nullable = false)
    private String genre;

    @Column(name = "release_date", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;

    public Movie(String name, String description, FilmType type, String genre, LocalDate releaseDate) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "name: " + name + ", release date: " + releaseDate;
    }
}
