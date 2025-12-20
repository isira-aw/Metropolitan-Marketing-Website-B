package com.marketing.controller;

import com.marketing.dto.response.HomeContentResponse;
import com.marketing.service.HomeContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/home")
@CrossOrigin(origins = "*")
public class PublicHomeContentController {

    @Autowired
    private HomeContentService homeContentService;

    /**
     * GET /api/public/home - Get home content for public display
     */
    @GetMapping
    public ResponseEntity<HomeContentResponse> getHomeContent() {
        try {
            return ResponseEntity.ok(homeContentService.getHomeContent());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}