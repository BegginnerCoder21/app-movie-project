package com.app_movie.app.movie.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    @NotBlank(message = "Le titre ne peut pas être vide")
    private String title;

    @NotBlank(message = "Le director ne peut pas être vide")
    private String director;

    @NotBlank(message = "Le studio ne peut pas être vide")
    private String studio;

    @NotBlank(message = "Le release year ne peut pas être vide")
    private Integer releaseYear;

    @NotBlank(message = "Le poster ne peut pas être vide")
    private String poster;
}
