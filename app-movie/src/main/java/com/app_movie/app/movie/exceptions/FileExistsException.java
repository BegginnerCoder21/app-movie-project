package com.app_movie.app.movie.exceptions;

public class FileExistsException extends RuntimeException{

    public FileExistsException(String message)
    {
        super(message);
    }
}
