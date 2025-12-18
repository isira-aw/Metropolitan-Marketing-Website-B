package com.marketing.mapper;

import com.marketing.dto.*;
import com.marketing.entity.GalleryItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GalleryItemMapper {

    public GalleryItemResponse toResponse(GalleryItem galleryItem) {
        if (galleryItem == null) {
            return null;
        }

        return GalleryItemResponse.builder()
                .id(galleryItem.getId())
                .title(galleryItem.getTitle())
                .imageUrl(galleryItem.getImageUrl())
                .description(galleryItem.getDescription())
                .category(galleryItem.getCategory())
                .displayOrder(galleryItem.getDisplayOrder())
                .createdAt(galleryItem.getCreatedAt())
                .updatedAt(galleryItem.getUpdatedAt())
                .build();
    }

    public List<GalleryItemResponse> toResponseList(List<GalleryItem> galleryItems) {
        return galleryItems.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GalleryItem toEntity(CreateGalleryItemRequest request) {
        if (request == null) {
            return null;
        }

        GalleryItem galleryItem = new GalleryItem();
        galleryItem.setTitle(request.getTitle());
        galleryItem.setImageUrl(request.getImageUrl());
        galleryItem.setDescription(request.getDescription());
        galleryItem.setCategory(request.getCategory());
        galleryItem.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        return galleryItem;
    }

    public void updateEntity(GalleryItem galleryItem, UpdateGalleryItemRequest request) {
        if (request == null) {
            return;
        }

        if (request.getTitle() != null) {
            galleryItem.setTitle(request.getTitle());
        }
        if (request.getImageUrl() != null) {
            galleryItem.setImageUrl(request.getImageUrl());
        }
        if (request.getDescription() != null) {
            galleryItem.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            galleryItem.setCategory(request.getCategory());
        }
        if (request.getDisplayOrder() != null) {
            galleryItem.setDisplayOrder(request.getDisplayOrder());
        }
    }
}
