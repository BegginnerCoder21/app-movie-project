package com.app_movie.app.movie.exceptions;

public class MovieNotExistsException extends RuntimeException{

    public MovieNotExistsException(String message)
    {
        super(message);
    }
}
