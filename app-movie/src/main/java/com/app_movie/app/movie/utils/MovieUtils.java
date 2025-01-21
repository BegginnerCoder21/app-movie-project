package com.app_movie.app.movie.utils;

import com.app_movie.app.movie.dto.MovieRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieUtils {

    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "3";
    public static final String SORT_BY = "id";
    public static final String SORT_DIR = "asc";

    public static MovieRequest convertToMovieResponse(String movieDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(movieDto, MovieRequest.class);
    }


}
