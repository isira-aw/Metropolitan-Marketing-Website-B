package com.marketing.service;

import com.marketing.entity.Brand;
import com.marketing.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAllByOrderByNameAsc();
    }

    public Brand createBrand(Brand brand) {
        if (brandRepository.existsByName(brand.getName())) {
            throw new RuntimeException("Brand already exists");
        }
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand updatedBrand) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        if (!brand.getName().equals(updatedBrand.getName()) &&
                brandRepository.existsByName(updatedBrand.getName())) {
            throw new RuntimeException("Brand name already exists");
        }

        brand.setName(updatedBrand.getName());
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}