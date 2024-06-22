package com.example.movie_test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    @NotNull
    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 2, max = 30)
    String name;

    @NotNull
    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 10, max = 300)
    private String description;

    @NotNull
    private FilmType type;

    @NotNull
    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 2, max = 20)
    private String genre;

    @NotNull(message = "Поле обязательно к заполнению")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return "name: " + this.name
                + ", description: " + description
                + ", type: " + type
                + ", genre: " + genre
                + ", release date: " + releaseDate;
    }
}
