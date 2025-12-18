package com.marketing.mapper;

import com.marketing.dto.*;
import com.marketing.entity.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(ProductCategory category) {
        if (category == null) {
            return null;
        }

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public List<CategoryResponse> toResponseList(List<ProductCategory> categories) {
        return categories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductCategory toEntity(CreateCategoryRequest request) {
        if (request == null) {
            return null;
        }

        ProductCategory category = new ProductCategory();
        category.setName(request.getName());

        return category;
    }

    public void updateEntity(ProductCategory category, UpdateCategoryRequest request) {
        if (request == null) {
            return;
        }

        if (request.getName() != null) {
            category.setName(request.getName());
        }
    }
}
