package com.marketing.service;

import com.marketing.dto.*;
import com.marketing.entity.Brand;
import com.marketing.mapper.BrandMapper;
import com.marketing.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAllByOrderByNameAsc();
        return brandMapper.toResponseList(brands);
    }

    public BrandResponse createBrand(CreateBrandRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            throw new RuntimeException("Brand already exists");
        }
        Brand brand = brandMapper.toEntity(request);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toResponse(savedBrand);
    }

    public BrandResponse updateBrand(Long id, UpdateBrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        if (!brand.getName().equals(request.getName()) &&
                brandRepository.existsByName(request.getName())) {
            throw new RuntimeException("Brand name already exists");
        }

        brandMapper.updateEntity(brand, request);
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.toResponse(updatedBrand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}