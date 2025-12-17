package com.marketing.controller;

import com.marketing.entity.ContactInfo;
import com.marketing.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/contact")
public class AdminContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @GetMapping
    public ResponseEntity<ContactInfo> getContactInfo() {
        return ResponseEntity.ok(contactInfoService.getContactInfo());
    }

    @PutMapping
    public ResponseEntity<ContactInfo> updateContactInfo(@RequestBody ContactInfo contactInfo) {
        try {
            return ResponseEntity.ok(contactInfoService.updateContactInfo(contactInfo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
