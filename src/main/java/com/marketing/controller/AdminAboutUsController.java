package com.marketing.controller;

import com.marketing.dto.request.AboutUsRequestDTO;
import com.marketing.dto.response.AboutUsResponseDTO;
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
    public ResponseEntity<AboutUsResponseDTO> getAboutUs() {
        return ResponseEntity.ok(aboutUsService.getAboutUs());
    }

    @PutMapping
    public ResponseEntity<AboutUsResponseDTO> updateAboutUs(
            @RequestBody AboutUsRequestDTO requestDTO) {

        return ResponseEntity.ok(aboutUsService.updateAboutUs(requestDTO));
    }
}
