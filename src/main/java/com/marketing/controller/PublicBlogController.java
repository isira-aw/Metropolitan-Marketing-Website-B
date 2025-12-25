package com.marketing.controller;

import com.marketing.dto.response.BlogResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/public/blogs")
@RequiredArgsConstructor
public class PublicBlogController {

    private final BlogService blogService;

    /**
     * Get all published blogs with pagination
     * GET /api/public/blogs?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<PageResponse<BlogResponse>> getAllPublishedBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getAllPublishedBlogs(page, size));
    }

    /**
     * Filter published blogs by division
     * GET /api/public/blogs/division/CentralAC?page=0&size=10
     */
    @GetMapping("/division/{division}")
    public ResponseEntity<PageResponse<BlogResponse>> getPublishedBlogsByDivision(
            @PathVariable String division,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getPublishedBlogsByDivision(division, page, size));
    }

    /**
     * Search published blogs by keyword
     * GET /api/public/blogs/search?keyword=solar&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<BlogResponse>> searchPublishedBlogs(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchPublishedBlogs(keyword, page, size));
    }

    /**
     * Filter by division AND search by keyword
     * GET /api/public/blogs/division/CentralAC/search?keyword=installation&page=0&size=10
     */
    @GetMapping("/division/{division}/search")
    public ResponseEntity<PageResponse<BlogResponse>> searchPublishedBlogsByDivision(
            @PathVariable String division,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchPublishedBlogsByDivision(division, keyword, page, size));
    }

    /**
     * Filter by date range
     * GET /api/public/blogs/date-range?startDate=2024-01-01&endDate=2024-12-31&page=0&size=10
     */
    @GetMapping("/date-range")
    public ResponseEntity<PageResponse<BlogResponse>> getPublishedBlogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getPublishedBlogsByDateRange(startDate, endDate, page, size));
    }

    /**
     * Get single blog by ID (increments view count)
     * GET /api/public/blogs/123
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
     * Get single blog by slug (increments view count)
     * GET /api/public/blogs/slug/solar-panel-installation-guide
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<BlogResponse> getBlogBySlug(@PathVariable String slug) {
        try {
            return ResponseEntity.ok(blogService.getBlogBySlug(slug));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get recent published blogs (top 10)
     * GET /api/public/blogs/recent
     */
    @GetMapping("/recent")
    public ResponseEntity<List<BlogResponse>> getRecentPublishedBlogs() {
        return ResponseEntity.ok(blogService.getRecentPublishedBlogs());
    }

    /**
     * Get all available divisions
     * GET /api/public/blogs/divisions
     */
    @GetMapping("/divisions")
    public ResponseEntity<List<String>> getAllDivisions() {
        return ResponseEntity.ok(blogService.getAllDivisions());
    }
}