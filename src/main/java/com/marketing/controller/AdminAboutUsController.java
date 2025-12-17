package com.marketing.controller;

import com.marketing.entity.AboutUs;
import com.marketing.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/about")
public class AdminAboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    @GetMapping
    public ResponseEntity<AboutUs> getAboutUs() {
        return ResponseEntity.ok(aboutUsService.getAboutUs());
    }

    @PutMapping
    public ResponseEntity<AboutUs> updateAboutUs(@RequestBody AboutUs aboutUs) {
        try {
            return ResponseEntity.ok(aboutUsService.updateAboutUs(aboutUs));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
