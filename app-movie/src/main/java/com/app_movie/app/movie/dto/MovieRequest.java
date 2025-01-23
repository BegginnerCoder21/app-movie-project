package com.app_movie.app.movie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    @NotBlank(message = "Le titre ne peut pas être vide")
    private String title;

    @NotBlank(message = "Le director ne peut pas être vide")
    private String director;

    @NotBlank(message = "Le studio ne peut pas être vide")
    private String studio;

    private Set<String> movieCast;

    private Integer releaseYear;

    @NotBlank(message = "Le poster ne peut pas être vide")
    private String poster;
}
