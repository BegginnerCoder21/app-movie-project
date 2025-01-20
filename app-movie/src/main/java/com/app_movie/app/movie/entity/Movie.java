package com.app_movie.app.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Set;

@Entity
@Table(name = "movies")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le titre ne peut pas être vide")

    private String title;
    @Column(nullable = false)

    @NotBlank(message = "Le director ne peut pas être vide")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Le studio ne peut pas être vide")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotNull(message = "Le release year ne peut pas être vide")
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Le poster ne peut pas être vide")
    private String poster;
}
