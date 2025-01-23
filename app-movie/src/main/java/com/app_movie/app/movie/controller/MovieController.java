package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.dto.MoviePageResponse;
import com.app_movie.app.movie.dto.MovieRequest;
import com.app_movie.app.movie.dto.MovieResponse;
import com.app_movie.app.movie.service.MovieService;
import com.app_movie.app.movie.utils.MovieUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<List<MovieResponse>> getAllMovies()
    {
        List<MovieResponse> movieResponseList = this.movieService.getAllMovies();

        return ResponseEntity.ok(movieResponseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Integer id, @RequestPart String request, @RequestPart MultipartFile file) throws IOException {

        MovieRequest movie = MovieUtils.convertToMovieResponse(request);

        MovieResponse movieResponse = this.movieService.updateMovie(id, movie, file);

        return ResponseEntity.ok(movieResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Integer id) throws IOException {
        String response = this.movieService.destroyMovie(id);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/pagination-movies")
    public ResponseEntity<MoviePageResponse> allMoviesPagination(
            @RequestParam(defaultValue = MovieUtils.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = MovieUtils.PAGE_SIZE, required = false) Integer pageSize
            )
    {
        MoviePageResponse moviePageResponse = this.movieService.getAllMoviesWithPagination(pageNumber, pageSize);

        return ResponseEntity.ok(moviePageResponse);
    }
    @GetMapping("/pagination-sorting-movies")
    public ResponseEntity<MoviePageResponse> allMoviesPaginationAndSorting(
            @RequestParam(defaultValue = MovieUtils.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = MovieUtils.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = MovieUtils.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = MovieUtils.SORT_DIR, required = false) String dir
    )
    {
        MoviePageResponse moviePageResponse = this.movieService.gelAllMoviesWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir);
        return ResponseEntity.ok(moviePageResponse);

    }


}
