package com.marketing.controller;

import com.marketing.dto.request.NewsRequest;
import com.marketing.dto.request.ReorderRequest;
import com.marketing.dto.response.NewsResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.entity.NewsCategory;
import com.marketing.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {

    private final NewsService newsService;

    /**
     * Get all news items with pagination
     * GET /api/admin/news?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<PageResponse<NewsResponse>> getAllNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.getAllItemsPaginated(page, size));
    }

    /**
     * Get all news items without pagination (for reordering)
     * GET /api/admin/news/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<NewsResponse>> getAllNewsNoPagination() {
        return ResponseEntity.ok(newsService.getAllItems());
    }

    /**
     * Get single news item by ID
     * GET /api/admin/news/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getNewsById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(newsService.getItemById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get news by category
     * GET /api/admin/news/category/CORPORATE?page=0&size=10
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<PageResponse<NewsResponse>> getNewsByCategory(
            @PathVariable NewsCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.getItemsByCategory(category, page, size, false));
    }

    /**
     * Search news
     * GET /api/admin/news/search?keyword=technology&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<NewsResponse>> searchNews(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.searchItems(keyword, page, size));
    }

    /**
     * Create new news item
     * POST /api/admin/news
     */
    @PostMapping
    public ResponseEntity<NewsResponse> createNews(@Valid @RequestBody NewsRequest request) {
        try {
            NewsResponse response = newsService.createItem(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update existing news item
     * PUT /api/admin/news/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> updateNews(
            @PathVariable Long id,
            @Valid @RequestBody NewsRequest request) {
        try {
            NewsResponse response = newsService.updateItem(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete news item
     * DELETE /api/admin/news/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Reorder news items
     * POST /api/admin/news/reorder
     * Body: { "itemIds": [1, 2, 3, 4] }
     */
    @PostMapping("/reorder")
    public ResponseEntity<Void> reorderNews(@Valid @RequestBody ReorderRequest request) {
        try {
            newsService.reorderItems(request.getItemIds());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Toggle publish status
     * PATCH /api/admin/news/{id}/toggle-publish
     */
    @PatchMapping("/{id}/toggle-publish")
    public ResponseEntity<NewsResponse> togglePublishStatus(@PathVariable Long id) {
        try {
            NewsResponse response = newsService.togglePublishStatus(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Toggle featured status
     * PATCH /api/admin/news/{id}/toggle-featured
     */
    @PatchMapping("/{id}/toggle-featured")
    public ResponseEntity<NewsResponse> toggleFeaturedStatus(@PathVariable Long id) {
        try {
            NewsResponse response = newsService.toggleFeaturedStatus(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}