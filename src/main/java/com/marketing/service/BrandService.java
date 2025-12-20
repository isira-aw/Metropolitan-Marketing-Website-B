package com.marketing.service;

import com.marketing.dto.request.BrandRequest;
import com.marketing.dto.response.BrandResponse;
import com.marketing.entity.Brand;
import com.marketing.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> getActiveBrands() {
        return brandRepository.findAll()
                .stream()
                .filter(brand -> brand.getIsActive() != null && brand.getIsActive())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        return mapToResponse(brand);
    }

    @Transactional
    public BrandResponse createBrand(BrandRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            throw new RuntimeException("Brand already exists with name: " + request.getName());
        }

        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setImageUrl(request.getImageUrl());
        brand.setLink(request.getLink());
        brand.setDescription(request.getDescription());
        brand.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        brand = brandRepository.save(brand);
        return mapToResponse(brand);
    }

    @Transactional
    public BrandResponse updateBrand(Long id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));

        // Check if name is being changed and if new name already exists
        if (!brand.getName().equals(request.getName()) &&
                brandRepository.existsByName(request.getName())) {
            throw new RuntimeException("Brand name already exists: " + request.getName());
        }

        brand.setName(request.getName());
        brand.setImageUrl(request.getImageUrl());
        brand.setLink(request.getLink());
        brand.setDescription(request.getDescription());
        if (request.getIsActive() != null) {
            brand.setIsActive(request.getIsActive());
        }

        brand = brandRepository.save(brand);
        return mapToResponse(brand);
    }

    @Transactional
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new RuntimeException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }

    private BrandResponse mapToResponse(Brand brand) {
        BrandResponse response = new BrandResponse();
        response.setId(brand.getId());
        response.setName(brand.getName());
        response.setImageUrl(brand.getImageUrl());
        response.setLink(brand.getLink());
        response.setDescription(brand.getDescription());
        response.setIsActive(brand.getIsActive());
        response.setCreatedAt(brand.getCreatedAt());
        response.setUpdatedAt(brand.getUpdatedAt());
        return response;
    }
}