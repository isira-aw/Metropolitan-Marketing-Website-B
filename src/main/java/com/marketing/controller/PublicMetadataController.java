package com.marketing.controller;

import com.marketing.entity.Brand;
import com.marketing.entity.ProductCategory;
import com.marketing.service.BrandService;
import com.marketing.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicMetadataController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}