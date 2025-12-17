package com.marketing.service;

import com.marketing.entity.Admin;
import com.marketing.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProfileService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin updateProfile(String currentUsername, Admin updatedAdmin, String newPassword) {
        Admin admin = getAdminByUsername(currentUsername);

        // Update username if changed and not taken
        if (!admin.getUsername().equals(updatedAdmin.getUsername())) {
            if (adminRepository.existsByUsername(updatedAdmin.getUsername())) {
                throw new RuntimeException("Username already taken");
            }
            admin.setUsername(updatedAdmin.getUsername());
        }

        // Update email if changed and not taken
        if (!admin.getEmail().equals(updatedAdmin.getEmail())) {
            if (adminRepository.existsByEmail(updatedAdmin.getEmail())) {
                throw new RuntimeException("Email already taken");
            }
            admin.setEmail(updatedAdmin.getEmail());
        }

        // Update password if provided
        if (newPassword != null && !newPassword.isEmpty()) {
            admin.setPassword(passwordEncoder.encode(newPassword));
        }

        // Update license
        if (updatedAdmin.getLicense() != null) {
            admin.setLicense(updatedAdmin.getLicense());
        }

        return adminRepository.save(admin);
    }

    public Admin updateAdminById(Long id, Admin updatedAdmin, String newPassword) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Update username if changed and not taken
        if (!admin.getUsername().equals(updatedAdmin.getUsername())) {
            if (adminRepository.existsByUsername(updatedAdmin.getUsername())) {
                throw new RuntimeException("Username already taken");
            }
            admin.setUsername(updatedAdmin.getUsername());
        }

        // Update email if changed and not taken
        if (!admin.getEmail().equals(updatedAdmin.getEmail())) {
            if (adminRepository.existsByEmail(updatedAdmin.getEmail())) {
                throw new RuntimeException("Email already taken");
            }
            admin.setEmail(updatedAdmin.getEmail());
        }

        // Update password if provided
        if (newPassword != null && !newPassword.isEmpty()) {
            admin.setPassword(passwordEncoder.encode(newPassword));
        }

        // Update license
        if (updatedAdmin.getLicense() != null) {
            admin.setLicense(updatedAdmin.getLicense());
        }

        return adminRepository.save(admin);
    }

    public Admin createAdmin(Admin newAdmin, String password) {
        if (adminRepository.existsByUsername(newAdmin.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (adminRepository.existsByEmail(newAdmin.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        newAdmin.setPassword(passwordEncoder.encode(password));
        if (newAdmin.getLicense() == null) {
            newAdmin.setLicense(true);
        }

        return adminRepository.save(newAdmin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}