package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.MoviePageResponse;
import com.app_movie.app.movie.dto.MovieRequest;
import com.app_movie.app.movie.dto.MovieResponse;
import com.app_movie.app.movie.entity.Movie;
import com.app_movie.app.movie.exceptions.EmptyException;
import com.app_movie.app.movie.exceptions.FileExistsException;
import com.app_movie.app.movie.exceptions.MovieNotExistsException;
import com.app_movie.app.movie.repository.MovieRepository;
import com.app_movie.app.movie.service.FileService;
import com.app_movie.app.movie.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    private final FileService fileService;

    @Value("${base.url}")
    private String baseUrl;

    private final MovieRepository movieRepository;

    private final ModelMapper modelMapper;

    @Value("${project.posters}")
    private String filePath;

    private static final String PATH_FILE_SEPARATOR = "/movie-api/management-file/";

    public MovieServiceImpl(FileService fileService, MovieRepository movieRepository, ModelMapper modelMapper) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public MovieResponse addMovie(MultipartFile file, MovieRequest movieRequest) throws IOException {

        var existingFile = Paths.get(this.filePath + File.separator + file.getOriginalFilename());
        if (file.isEmpty())
        {
            throw new EmptyException("Ajout d'un fichier est obligatoire");
        }

        if(Files.exists(existingFile))
        {
            throw new FileExistsException("Le nom du fichier selectionné existe déjà: " +file.getOriginalFilename());
        }

        String uploadedFileName = this.fileService.uploadFile(filePath, file);
        movieRequest.setPoster(uploadedFileName);

        Movie movie = this.modelMapper.map(movieRequest, Movie.class);
        log.info("Demarage du stockage du film: {}", movie);
        this.movieRepository.save(movie);

        String posterUrl = baseUrl + PATH_FILE_SEPARATOR + uploadedFileName;

        MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
        movieResponse.setPosterUrl(posterUrl);
        log.info("Envoie de la reponse: {}", movieResponse);

        return movieResponse;
    }

    @Transactional
    @Override
    public MovieResponse getMovie(Integer id) {

        Optional<Movie> movie = this.movieRepository.findById(id);

        if(movie.isEmpty())
        {
            throw new MovieNotExistsException("Aucun film n'a été trouvé avec l'id: " +id);
        }

        String posterUrl = baseUrl + PATH_FILE_SEPARATOR + movie.get().getPoster();

        MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
        movieResponse.setPosterUrl(posterUrl);

        return movieResponse;
    }

    @Transactional
    @Override
    public List<MovieResponse> getAllMovies() {

        List<Movie> movies = this.movieRepository.findAll();

        List<MovieResponse> movieResponseList = new ArrayList<>();
        for (Movie movie: movies)
        {
            String posterUrl = baseUrl + PATH_FILE_SEPARATOR + movie.getPoster();
            MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
            movieResponse.setPosterUrl(posterUrl);

            movieResponseList.add(movieResponse);
        }
        return movieResponseList;
    }

    @Transactional
    @Override
    public MovieResponse updateMovie(Integer id, MovieRequest movieRequest, MultipartFile file) throws IOException {

        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotExistsException("Aucun film n'a été trouvé avec l'id: " +id));

        String fileName = movie.getPoster();

        if (!file.isEmpty()) {
            log.info("Suppression du fichier existant !");
            var files = Paths.get(this.filePath + File.separator + fileName);
            Files.deleteIfExists(files);

            fileName = this.fileService.uploadFile(this.filePath, file);
        }

        movieRequest.setPoster(fileName);

        if (movieRequest.getMovieCast() != null) {
            Set<String> updatedMovieCast = movieRequest.getMovieCast();

            log.info("Suppression des elements n'étant plus present dans la requête");
            movie.getMovieCast().removeIf(existingMovieCast -> !updatedMovieCast.contains(existingMovieCast));

            log.info("ajout des elements ajouter a movie cast lors de la mise à jour");
            updatedMovieCast.stream().map(newMovieCast -> {
                movie.getMovieCast().add(newMovieCast);
                return null;
            }).collect(Collectors.toSet());

        }
        log.info("movie mis a jour !");
        this.modelMapper.map(movieRequest, movie);

        log.info("Démarrage de la mise à jour du film: {}", movie);
        this.movieRepository.save(movie);

        String posterUrl = this.baseUrl + PATH_FILE_SEPARATOR + fileName;
        MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
        movieResponse.setPosterUrl(posterUrl);

        return movieResponse;
    }


    @Override
    public String destroyMovie(Integer id) throws IOException {

        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new MovieNotExistsException("Aucun film avec l'identifiant " + id + " n'a été trouvé !"));

        Files.deleteIfExists(Paths.get(filePath + File.separator + movie.getPoster()));

        this.movieRepository.deleteById(id);

        return "Le film avec l'identifiant " + id + " a bien été supprimé";
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Movie> movies = this.movieRepository.findAll(pageable);

        List<MovieResponse> movieResponseList = new ArrayList<>();
        for (Movie movie: movies)
        {
            String posterUrl = baseUrl + PATH_FILE_SEPARATOR + movie.getPoster();
            MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
            movieResponse.setPosterUrl(posterUrl);

            movieResponseList.add(movieResponse);
        }

        return new MoviePageResponse(movieResponseList, pageNumber, pageSize, movies.getTotalElements(), movies.getTotalPages(), movies.isLast());
    }

    @Override
    public MoviePageResponse gelAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {

        Sort sort =  dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Movie> movies = this.movieRepository.findAll(pageable);

        List<MovieResponse> movieResponseList = new ArrayList<>();
        for (Movie movie: movies)
        {
            String posterUrl = baseUrl + PATH_FILE_SEPARATOR + movie.getPoster();
            MovieResponse movieResponse = this.modelMapper.map(movie, MovieResponse.class);
            movieResponse.setPosterUrl(posterUrl);

            movieResponseList.add(movieResponse);
        }

        return new MoviePageResponse(movieResponseList, pageNumber, pageSize, movies.getTotalElements(), movies.getTotalPages(), movies.isLast());
    }
}
