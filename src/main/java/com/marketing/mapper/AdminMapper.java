package com.marketing.mapper;

import com.marketing.dto.*;
import com.marketing.entity.Admin;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminMapper {

    public AdminResponse toResponse(Admin admin) {
        if (admin == null) {
            return null;
        }

        return AdminResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .license(admin.getLicense())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    public AdminProfileResponse toProfileResponse(Admin admin) {
        if (admin == null) {
            return null;
        }

        return AdminProfileResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .license(admin.getLicense())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    public List<AdminResponse> toResponseList(List<Admin> admins) {
        return admins.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Admin toEntity(CreateAdminRequest request) {
        if (request == null) {
            return null;
        }

        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setLicense(request.getLicense() != null ? request.getLicense() : true);

        return admin;
    }

    public void updateEntity(Admin admin, UpdateAdminRequest request) {
        if (request == null) {
            return;
        }

        if (request.getUsername() != null) {
            admin.setUsername(request.getUsername());
        }
        if (request.getEmail() != null) {
            admin.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            admin.setPassword(request.getPassword());
        }
        if (request.getLicense() != null) {
            admin.setLicense(request.getLicense());
        }
    }
}
