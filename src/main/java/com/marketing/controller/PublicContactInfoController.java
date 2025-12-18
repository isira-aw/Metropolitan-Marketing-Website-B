package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/contact")
public class PublicContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @GetMapping
    public ResponseEntity<ContactInfoResponse> getContactInfo() {
        return ResponseEntity.ok(contactInfoService.getContactInfo());
    }
}
