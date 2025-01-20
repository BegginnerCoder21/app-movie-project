package com.app_movie.app.movie.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieResponse {

    private Integer id;
    private String title;
    private String director;
    private String studio;
    private Set<String> movieCast;
    private Integer releaseYear;
    private String poster;
    private String posterUrl;
}
