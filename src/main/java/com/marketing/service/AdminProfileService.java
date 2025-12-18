package com.marketing.service;

import com.marketing.dto.*;
import com.marketing.entity.Admin;
import com.marketing.mapper.AdminMapper;
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

    @Autowired
    private AdminMapper adminMapper;

    public AdminProfileResponse getAdminProfileByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return adminMapper.toProfileResponse(admin);
    }

    public List<AdminResponse> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return adminMapper.toResponseList(admins);
    }

    public AdminProfileResponse updateProfile(String currentUsername, UpdateAdminRequest request) {
        Admin admin = adminRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Update username if changed and not taken
        if (request.getUsername() != null && !admin.getUsername().equals(request.getUsername())) {
            if (adminRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Username already taken");
            }
            admin.setUsername(request.getUsername());
        }

        // Update email if changed and not taken
        if (request.getEmail() != null && !admin.getEmail().equals(request.getEmail())) {
            if (adminRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already taken");
            }
            admin.setEmail(request.getEmail());
        }

        // Update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Update license
        if (request.getLicense() != null) {
            admin.setLicense(request.getLicense());
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return adminMapper.toProfileResponse(updatedAdmin);
    }

    public AdminResponse updateAdminById(Long id, UpdateAdminRequest request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Update username if changed and not taken
        if (request.getUsername() != null && !admin.getUsername().equals(request.getUsername())) {
            if (adminRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Username already taken");
            }
            admin.setUsername(request.getUsername());
        }

        // Update email if changed and not taken
        if (request.getEmail() != null && !admin.getEmail().equals(request.getEmail())) {
            if (adminRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already taken");
            }
            admin.setEmail(request.getEmail());
        }

        // Update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Update license
        if (request.getLicense() != null) {
            admin.setLicense(request.getLicense());
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return adminMapper.toResponse(updatedAdmin);
    }

    public AdminResponse createAdmin(CreateAdminRequest request) {
        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Admin admin = adminMapper.toEntity(request);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);
        return adminMapper.toResponse(savedAdmin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}