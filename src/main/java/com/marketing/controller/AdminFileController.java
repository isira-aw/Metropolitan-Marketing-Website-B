package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/files")
public class AdminFileController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/unused")
    public ResponseEntity<UnusedFilesResponse> getUnusedImages() {
        try {
            List<String> unusedImages = fileStorageService.findUnusedImages();
            UnusedFilesResponse response = UnusedFilesResponse.builder()
                    .unusedFiles(unusedImages)
                    .count(unusedImages.size())
                    .message("Found " + unusedImages.size() + " unused files")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(UnusedFilesResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/unused")
    public ResponseEntity<UnusedFilesResponse> deleteUnusedImages() {
        try {
            List<String> unusedImages = fileStorageService.findUnusedImages();
            int count = unusedImages.size();
            fileStorageService.deleteUnusedImages();

            UnusedFilesResponse response = UnusedFilesResponse.builder()
                    .unusedFiles(unusedImages)
                    .count(count)
                    .message("Deleted " + count + " unused images")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(UnusedFilesResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build());
        }
    }
}