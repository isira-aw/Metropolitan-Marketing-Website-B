package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/upload")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileStorageService.storeFile(file);
            FileUploadResponse response = FileUploadResponse.builder()
                    .fileName(file.getOriginalFilename())
                    .fileUrl(fileUrl)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .message("File uploaded successfully")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(FileUploadResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }
}
