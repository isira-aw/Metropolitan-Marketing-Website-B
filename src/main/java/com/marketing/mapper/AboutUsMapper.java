package com.marketing.mapper;

import com.marketing.dto.*;
import com.marketing.entity.AboutUs;
import org.springframework.stereotype.Component;

@Component
public class AboutUsMapper {

    public AboutUsResponse toResponse(AboutUs aboutUs) {
        if (aboutUs == null) {
            return null;
        }

        return AboutUsResponse.builder()
                .id(aboutUs.getId())
                .companyName(aboutUs.getCompanyName())
                .companyDescription(aboutUs.getCompanyDescription())
                .ownerName(aboutUs.getOwnerName())
                .ownerTitle(aboutUs.getOwnerTitle())
                .ownerDescription(aboutUs.getOwnerDescription())
                .ownerImageUrl(aboutUs.getOwnerImageUrl())
                .updatedAt(aboutUs.getUpdatedAt())
                .build();
    }

    public void updateEntity(AboutUs aboutUs, UpdateAboutUsRequest request) {
        if (request == null) {
            return;
        }

        if (request.getCompanyName() != null) {
            aboutUs.setCompanyName(request.getCompanyName());
        }
        if (request.getCompanyDescription() != null) {
            aboutUs.setCompanyDescription(request.getCompanyDescription());
        }
        if (request.getOwnerName() != null) {
            aboutUs.setOwnerName(request.getOwnerName());
        }
        if (request.getOwnerTitle() != null) {
            aboutUs.setOwnerTitle(request.getOwnerTitle());
        }
        if (request.getOwnerDescription() != null) {
            aboutUs.setOwnerDescription(request.getOwnerDescription());
        }
        if (request.getOwnerImageUrl() != null) {
            aboutUs.setOwnerImageUrl(request.getOwnerImageUrl());
        }
    }
}
