package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/movie-api/management-file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("")
    public String uploadFile(MultipartFile file, @RequestBody String filePath) throws IOException {

        return this.fileService.uploadFile(filePath, file);
    }
}
