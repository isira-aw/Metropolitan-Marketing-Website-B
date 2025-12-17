package com.marketing.controller;

import com.marketing.entity.GalleryItem;
import com.marketing.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/gallery")
public class AdminGalleryController {

    @Autowired
    private GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<GalleryItem>> getAllItems() {
        return ResponseEntity.ok(galleryService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryItem> getItemById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(galleryService.getItemById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GalleryItem> createItem(@RequestBody GalleryItem item) {
        try {
            return ResponseEntity.ok(galleryService.createItem(item));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalleryItem> updateItem(@PathVariable Long id, @RequestBody GalleryItem item) {
        try {
            return ResponseEntity.ok(galleryService.updateItem(id, item));
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
    public ResponseEntity<Void> reorderItems(@RequestBody Map<String, List<Long>> payload) {
        try {
            List<Long> itemIds = payload.get("itemIds");
            galleryService.reorderItems(itemIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
