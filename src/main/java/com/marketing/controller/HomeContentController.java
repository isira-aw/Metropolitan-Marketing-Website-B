package com.marketing.controller;

import com.marketing.dto.request.HomeContentRequest;
import com.marketing.dto.response.HomeContentResponse;
import com.marketing.service.HomeContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/home")
public class HomeContentController {

    @Autowired
    private HomeContentService homeContentService;

    /**
     * GET /api/admin/home - Get home content
     */
    @GetMapping
    public ResponseEntity<HomeContentResponse> getHomeContent() {
        try {
            return ResponseEntity.ok(homeContentService.getHomeContent());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * PUT /api/admin/home - Update home content
     */
    @PutMapping
    public ResponseEntity<?> updateHomeContent(@RequestBody HomeContentRequest request) {
        try {
            HomeContentResponse response = homeContentService.updateHomeContent(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}