package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/movie-api/management-file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.posters}")
    private String filePath;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException {

        String fileName = this.fileService.uploadFile(filePath, file);

        return ResponseEntity.ok("Fichier télechargé: " + fileName);
    }

    @GetMapping("/{fileName}")
    public void getResourceFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile = this.fileService.getResourceFile(filePath, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}
