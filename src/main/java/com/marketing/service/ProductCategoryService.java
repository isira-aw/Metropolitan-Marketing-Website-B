package com.marketing.service;

import com.marketing.entity.ProductCategory;
import com.marketing.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAllByOrderByNameAsc();
    }

    public ProductCategory createCategory(ProductCategory category) {
        if (productCategoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists");
        }
        return productCategoryRepository.save(category);
    }

    public ProductCategory updateCategory(Long id, ProductCategory updatedCategory) {
        ProductCategory category = productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getName().equals(updatedCategory.getName()) &&
                productCategoryRepository.existsByName(updatedCategory.getName())) {
            throw new RuntimeException("Category name already exists");
        }

        category.setName(updatedCategory.getName());
        return productCategoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }
}