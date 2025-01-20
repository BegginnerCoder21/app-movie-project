package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String filePath, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        String path = filePath + File.separator + fileName;

        File fileStorage = new File(filePath);

        if(!fileStorage.exists())
        {
            fileStorage.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String filePath, String fileName) {
        return null;
    }
}
