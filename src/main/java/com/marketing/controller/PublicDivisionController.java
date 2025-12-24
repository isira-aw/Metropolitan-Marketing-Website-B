package com.marketing.controller;

import com.marketing.dto.response.DivisionResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.service.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/divisions")
@RequiredArgsConstructor
public class PublicDivisionController {

    private final DivisionService divisionService;

    /**
     * Get all active divisions (for listing page)
     * GET /api/public/divisions
     */
    @GetMapping
    public ResponseEntity<List<DivisionResponse>> getActiveDivisions() {
        return ResponseEntity.ok(divisionService.getAllActiveDivisions());
    }

    /**
     * Get active divisions with pagination
     * GET /api/public/divisions/paginated?page=0&size=10
     */
    @GetMapping("/paginated")
    public ResponseEntity<PageResponse<DivisionResponse>> getActiveDivisionsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(divisionService.getActiveDivisionsPaginated(page, size));
    }

    /**
     * Get single division by ID (for detail page)
     * GET /api/public/divisions/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DivisionResponse> getDivisionById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(divisionService.getDivisionById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get division by slug (SEO-friendly URL)
     * GET /api/public/divisions/slug/{slug}
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<DivisionResponse> getDivisionBySlug(@PathVariable String slug) {
        try {
            return ResponseEntity.ok(divisionService.getDivisionBySlug(slug));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}