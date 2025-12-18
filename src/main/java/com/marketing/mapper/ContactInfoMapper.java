package com.marketing.mapper;

import com.marketing.dto.*;
import com.marketing.entity.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoMapper {

    public ContactInfoResponse toResponse(ContactInfo contactInfo) {
        if (contactInfo == null) {
            return null;
        }

        return ContactInfoResponse.builder()
                .id(contactInfo.getId())
                .email(contactInfo.getEmail())
                .phone(contactInfo.getPhone())
                .address(contactInfo.getAddress())
                .facebookUrl(contactInfo.getFacebookUrl())
                .twitterUrl(contactInfo.getTwitterUrl())
                .instagramUrl(contactInfo.getInstagramUrl())
                .linkedinUrl(contactInfo.getLinkedinUrl())
                .updatedAt(contactInfo.getUpdatedAt())
                .build();
    }

    public void updateEntity(ContactInfo contactInfo, UpdateContactInfoRequest request) {
        if (request == null) {
            return;
        }

        if (request.getEmail() != null) {
            contactInfo.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            contactInfo.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            contactInfo.setAddress(request.getAddress());
        }
        if (request.getFacebookUrl() != null) {
            contactInfo.setFacebookUrl(request.getFacebookUrl());
        }
        if (request.getTwitterUrl() != null) {
            contactInfo.setTwitterUrl(request.getTwitterUrl());
        }
        if (request.getInstagramUrl() != null) {
            contactInfo.setInstagramUrl(request.getInstagramUrl());
        }
        if (request.getLinkedinUrl() != null) {
            contactInfo.setLinkedinUrl(request.getLinkedinUrl());
        }
    }
}
