package com.marketing.mapper;

import com.marketing.dto.*;
import com.marketing.entity.Brand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrandMapper {

    public BrandResponse toResponse(Brand brand) {
        if (brand == null) {
            return null;
        }

        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .createdAt(brand.getCreatedAt())
                .updatedAt(brand.getUpdatedAt())
                .build();
    }

    public List<BrandResponse> toResponseList(List<Brand> brands) {
        return brands.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Brand toEntity(CreateBrandRequest request) {
        if (request == null) {
            return null;
        }

        Brand brand = new Brand();
        brand.setName(request.getName());

        return brand;
    }

    public void updateEntity(Brand brand, UpdateBrandRequest request) {
        if (request == null) {
            return;
        }

        if (request.getName() != null) {
            brand.setName(request.getName());
        }
    }
}
