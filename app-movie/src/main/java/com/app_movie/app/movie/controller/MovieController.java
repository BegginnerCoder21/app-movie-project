package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.dto.MovieRequest;
import com.app_movie.app.movie.dto.MovieResponse;
import com.app_movie.app.movie.service.MovieService;
import com.app_movie.app.movie.utils.MovieUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("api/movie")
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add-movie")
    public ResponseEntity<MovieResponse> addMovie(@RequestPart MultipartFile file,@RequestPart String request) throws IOException {

        MovieRequest movie = MovieUtils.convertToMovieResponse(request);
        MovieResponse movieResponse = this.movieService.addMovie(file, movie);

        return new ResponseEntity<>(movieResponse,HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Integer id)
    {
        MovieResponse movieResponse = this.movieService.getMovie(id);

        return ResponseEntity.ok(movieResponse);
    }


}
