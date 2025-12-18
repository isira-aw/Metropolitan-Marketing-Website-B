package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.GalleryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/gallery")
public class AdminGalleryController {

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

    @PostMapping
    public ResponseEntity<GalleryItemResponse> createItem(@Valid @RequestBody CreateGalleryItemRequest request) {
        try {
            return ResponseEntity.ok(galleryService.createItem(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalleryItemResponse> updateItem(@PathVariable Long id, @Valid @RequestBody UpdateGalleryItemRequest request) {
        try {
            return ResponseEntity.ok(galleryService.updateItem(id, request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            galleryService.deleteItem(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reorder")
    public ResponseEntity<Void> reorderItems(@Valid @RequestBody ReorderGalleryRequest request) {
        try {
            galleryService.reorderItems(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
