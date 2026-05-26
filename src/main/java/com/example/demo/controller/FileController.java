package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class FileController {
    private final String uploadDir = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            String fileUrl = "http://localhost:8080/files/" + fileName;

            return ResponseEntity.ok(Map.of("url", fileUrl));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed");
        }
    }
}
