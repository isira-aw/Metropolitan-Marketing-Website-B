package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.ContactInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/contact")
public class AdminContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @GetMapping
    public ResponseEntity<ContactInfoResponse> getContactInfo() {
        return ResponseEntity.ok(contactInfoService.getContactInfo());
    }

    @PutMapping
    public ResponseEntity<ContactInfoResponse> updateContactInfo(@Valid @RequestBody UpdateContactInfoRequest request) {
        try {
            return ResponseEntity.ok(contactInfoService.updateContactInfo(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
