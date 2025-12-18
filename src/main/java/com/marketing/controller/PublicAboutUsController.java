package com.marketing.controller;

import com.marketing.dto.response.AboutUsResponseDTO;
import com.marketing.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/about")
public class PublicAboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    @GetMapping
    public ResponseEntity<AboutUsResponseDTO> getAboutUs() {
        return ResponseEntity.ok(aboutUsService.getAboutUs());
    }
}
