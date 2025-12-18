package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.AboutUsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/about")
public class AdminAboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    @GetMapping
    public ResponseEntity<AboutUsResponse> getAboutUs() {
        return ResponseEntity.ok(aboutUsService.getAboutUs());
    }

    @PutMapping
    public ResponseEntity<AboutUsResponse> updateAboutUs(@Valid @RequestBody UpdateAboutUsRequest request) {
        try {
            return ResponseEntity.ok(aboutUsService.updateAboutUs(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
