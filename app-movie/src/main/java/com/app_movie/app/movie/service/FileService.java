package com.app_movie.app.movie.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    public String uploadFile(String filePath, MultipartFile file) throws IOException;

    public InputStream getResourceFile(String filePath, String fileName);
}
