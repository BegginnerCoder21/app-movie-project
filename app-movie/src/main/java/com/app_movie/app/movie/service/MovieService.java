package com.app_movie.app.movie.service;

import com.app_movie.app.movie.dto.MovieRequest;
import com.app_movie.app.movie.dto.MovieResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    public MovieResponse addMovie(MultipartFile file, MovieRequest movieRequest) throws IOException;

    public MovieResponse getMovie(Integer id);

    public List<MovieResponse> getAllMovies();

    public MovieResponse updateMovie(Integer id, MovieRequest movieRequest, MultipartFile file) throws IOException;

    public String destroyMovie(Integer id);
}
