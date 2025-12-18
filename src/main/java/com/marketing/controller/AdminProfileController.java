package com.marketing.controller;

import com.marketing.dto.*;
import com.marketing.service.AdminProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminProfileController {

    @Autowired
    private AdminProfileService adminProfileService;

    @GetMapping("/profile")
    public ResponseEntity<AdminProfileResponse> getProfile(Authentication authentication) {
        try {
            String username = authentication.getName();
            AdminProfileResponse profile = adminProfileService.getAdminProfileByUsername(username);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        try {
            String currentUsername = authentication.getName();
            AdminProfileResponse profile = adminProfileService.updateProfile(currentUsername, request);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/admins")
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {
        List<AdminResponse> admins = adminProfileService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/admins")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateAdminRequest request) {
        try {
            AdminResponse admin = adminProfileService.createAdmin(request);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/admins/{id}")
    public ResponseEntity<?> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        try {
            AdminResponse admin = adminProfileService.updateAdminById(id, request);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/admins/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            adminProfileService.deleteAdmin(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}