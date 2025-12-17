package com.marketing.controller;

import com.marketing.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/files")
public class AdminFileController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/unused")
    public ResponseEntity<Map<String, Object>> getUnusedImages() {
        try {
            List<String> unusedImages = fileStorageService.findUnusedImages();
            Map<String, Object> response = new HashMap<>();
            response.put("count", unusedImages.size());
            response.put("files", unusedImages);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/unused")
    public ResponseEntity<Map<String, String>> deleteUnusedImages() {
        try {
            List<String> unusedImages = fileStorageService.findUnusedImages();
            int count = unusedImages.size();
            fileStorageService.deleteUnusedImages();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted " + count + " unused images");
            response.put("count", String.valueOf(count));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}