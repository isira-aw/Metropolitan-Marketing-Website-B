package com.marketing.service;

import com.marketing.dto.*;
import com.marketing.entity.Product;
import com.marketing.mapper.ProductMapper;
import com.marketing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public PageResponse<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productMapper.toPageResponse(productPage);
    }

    public PageResponse<ProductResponse> searchProducts(String search, String category, String brand, Pageable pageable) {
        Page<Product> productPage = productRepository.searchProducts(search, category, brand, pageable);
        return productMapper.toPageResponse(productPage);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponse(product);
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateEntity(product, request);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}