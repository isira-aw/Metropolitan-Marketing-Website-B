package com.marketing.controller;

import com.marketing.entity.Admin;
import com.marketing.service.AdminProfileService;
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
    public ResponseEntity<Admin> getProfile(Authentication authentication) {
        try {
            String username = authentication.getName();
            Admin admin = adminProfileService.getAdminByUsername(username);
            // Don't send password to frontend
            admin.setPassword(null);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @RequestBody Map<String, Object> payload
    ) {
        try {
            String currentUsername = authentication.getName();

            Admin updatedAdmin = new Admin();
            updatedAdmin.setUsername((String) payload.get("username"));
            updatedAdmin.setEmail((String) payload.get("email"));
            updatedAdmin.setLicense((Boolean) payload.get("license"));

            String newPassword = (String) payload.get("password");

            Admin admin = adminProfileService.updateProfile(currentUsername, updatedAdmin, newPassword);
            admin.setPassword(null);

            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminProfileService.getAllAdmins();
        // Remove passwords
        admins.forEach(admin -> admin.setPassword(null));
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/admins")
    public ResponseEntity<?> createAdmin(@RequestBody Map<String, Object> payload) {
        try {
            Admin newAdmin = new Admin();
            newAdmin.setUsername((String) payload.get("username"));
            newAdmin.setEmail((String) payload.get("email"));
            newAdmin.setLicense((Boolean) payload.getOrDefault("license", true));

            String password = (String) payload.get("password");
            if (password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Password is required"));
            }

            Admin admin = adminProfileService.createAdmin(newAdmin, password);
            admin.setPassword(null);

            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/admins/{id}")
    public ResponseEntity<?> updateAdmin(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload
    ) {
        try {
            Admin updatedAdmin = new Admin();
            updatedAdmin.setUsername((String) payload.get("username"));
            updatedAdmin.setEmail((String) payload.get("email"));
            updatedAdmin.setLicense((Boolean) payload.get("license"));

            String newPassword = (String) payload.get("password");

            Admin admin = adminProfileService.updateAdminById(id, updatedAdmin, newPassword);
            admin.setPassword(null);

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