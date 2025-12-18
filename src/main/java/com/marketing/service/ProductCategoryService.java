package com.marketing.service;

import com.marketing.dto.*;
import com.marketing.entity.ProductCategory;
import com.marketing.mapper.CategoryMapper;
import com.marketing.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategories() {
        List<ProductCategory> categories = productCategoryRepository.findAllByOrderByNameAsc();
        return categoryMapper.toResponseList(categories);
    }

    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if (productCategoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists");
        }
        ProductCategory category = categoryMapper.toEntity(request);
        ProductCategory savedCategory = productCategoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        ProductCategory category = productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getName().equals(request.getName()) &&
                productCategoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category name already exists");
        }

        categoryMapper.updateEntity(category, request);
        ProductCategory updatedCategory = productCategoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    public void deleteCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }
}