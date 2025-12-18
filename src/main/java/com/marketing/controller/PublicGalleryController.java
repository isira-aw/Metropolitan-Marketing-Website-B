package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/gallery")
public class PublicGalleryController {

    @Autowired
    private GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<GalleryItemResponse>> getAllItems() {
        return ResponseEntity.ok(galleryService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryItemResponse> getItemById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(galleryService.getItemById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
