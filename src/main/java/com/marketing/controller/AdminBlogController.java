package com.marketing.controller;

import com.marketing.dto.request.BlogRequest;
import com.marketing.dto.response.BlogResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/blogs")
@RequiredArgsConstructor
public class AdminBlogController {

    private final BlogService blogService;

    /**
     * Get all blogs with pagination
     * GET /api/admin/blogs?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<PageResponse<BlogResponse>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getAllBlogs(page, size));
    }

    /**
     * Filter blogs by division
     * GET /api/admin/blogs/division/CentralAC?page=0&size=10
     */
    @GetMapping("/division/{division}")
    public ResponseEntity<PageResponse<BlogResponse>> getBlogsByDivision(
            @PathVariable String division,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getBlogsByDivision(division, page, size));
    }

    /**
     * Search blogs by keyword
     * GET /api/admin/blogs/search?keyword=solar&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<BlogResponse>> searchAllBlogs(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchAllBlogs(keyword, page, size));
    }

    /**
     * Filter by division AND search by keyword
     * GET /api/admin/blogs/division/CentralAC/search?keyword=installation&page=0&size=10
     */
    @GetMapping("/division/{division}/search")
    public ResponseEntity<PageResponse<BlogResponse>> searchBlogsByDivision(
            @PathVariable String division,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchBlogsByDivision(division, keyword, page, size));
    }

    /**
     * Filter by date range
     * GET /api/admin/blogs/date-range?startDate=2024-01-01&endDate=2024-12-31&page=0&size=10
     */
    @GetMapping("/date-range")
    public ResponseEntity<PageResponse<BlogResponse>> getBlogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getBlogsByDateRange(startDate, endDate, page, size));
    }

    /**
     * Get all available divisions
     * GET /api/admin/blogs/divisions
     */
    @GetMapping("/divisions")
    public ResponseEntity<List<String>> getAllDivisions() {
        return ResponseEntity.ok(blogService.getAllDivisions());
    }

    /**
     * Get single blog by ID
     * GET /api/admin/blogs/123
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(blogService.getBlogById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create new blog
     * POST /api/admin/blogs
     */
    @PostMapping
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogRequest request) {
        try {
            BlogResponse response = blogService.createBlog(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Update existing blog
     * PUT /api/admin/blogs/123
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(
            @PathVariable Long id,
            @Valid @RequestBody BlogRequest request) {
        try {
            BlogResponse response = blogService.updateBlog(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete blog
     * DELETE /api/admin/blogs/123
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        try {
            blogService.deleteBlog(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Toggle publish status
     * PATCH /api/admin/blogs/123/toggle-publish
     */
    @PatchMapping("/{id}/toggle-publish")
    public ResponseEntity<BlogResponse> togglePublishStatus(@PathVariable Long id) {
        try {
            BlogResponse response = blogService.togglePublishStatus(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}