package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.MovieRequest;
import com.app_movie.app.movie.dto.MovieResponse;
import com.app_movie.app.movie.entity.Movie;
import com.app_movie.app.movie.repository.MovieRepository;
import com.app_movie.app.movie.service.FileService;
import com.app_movie.app.movie.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private FileService fileService;

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.posters}")
    private String filePath;

    @Override
    public MovieResponse addMovie(MultipartFile file, MovieRequest movieRequest) throws IOException {

        String uploadedFileName = this.fileService.uploadFile(filePath, file);
        movieRequest.setPoster(uploadedFileName);

        Movie movie = this.modelMapper.map(movieRequest, Movie.class);
        log.info("Demarage du stockage du film: {}", movie);
        this.movieRepository.save(movie);

        String posterUrl = baseUrl + "/movie-api/management-file/" + uploadedFileName;

        MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
        movieResponse.setPosterUrl(posterUrl);
        log.info("Envoie de la reponse: {}", movieResponse);

        return movieResponse;
    }

    @Override
    public MovieResponse getMovie(Integer id) {

        Optional<Movie> movie = this.movieRepository.findById(id);

        if(movie.isEmpty())
        {
            throw new IllegalArgumentException("Aucun film n'a été trouvé !");
        }

        String posterUrl = baseUrl + "/movie-api/management-file/" + movie.get().getPoster();

        MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
        movieResponse.setPosterUrl(posterUrl);

        return movieResponse;
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return List.of();
    }
}
