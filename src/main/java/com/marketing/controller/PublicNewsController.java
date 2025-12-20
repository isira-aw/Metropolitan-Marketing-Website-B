package com.marketing.controller;

import com.marketing.dto.response.NewsResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.entity.NewsCategory;
import com.marketing.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/news")
@RequiredArgsConstructor
public class PublicNewsController {

    private final NewsService newsService;

    /**
     * Get all published news with pagination
     * GET /api/public/news?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<PageResponse<NewsResponse>> getPublishedNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.getPublishedItems(page, size));
    }

    /**
     * Get active news (published and not expired)
     * GET /api/public/news/active?page=0&size=10
     */
    @GetMapping("/active")
    public ResponseEntity<PageResponse<NewsResponse>> getActiveNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.getActiveNews(page, size));
    }

    /**
     * Get single news item by ID (increments view count)
     * GET /api/public/news/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getNewsById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(newsService.getItemByIdAndIncrementView(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get news by category with pagination
     * GET /api/public/news/category/CORPORATE?page=0&size=10
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<PageResponse<NewsResponse>> getNewsByCategory(
            @PathVariable NewsCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.getItemsByCategory(category, page, size, true));
    }

    /**
     * Get featured news
     * GET /api/public/news/featured
     */
    @GetMapping("/featured")
    public ResponseEntity<List<NewsResponse>> getFeaturedNews() {
        return ResponseEntity.ok(newsService.getFeaturedNews());
    }

    /**
     * Search news
     * GET /api/public/news/search?keyword=technology&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<NewsResponse>> searchNews(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.searchItems(keyword, page, size));
    }
}