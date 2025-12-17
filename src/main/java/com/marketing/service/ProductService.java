package com.marketing.service;

import com.marketing.entity.Product;
import com.marketing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(String search, String category, String brand, Pageable pageable) {
        return productRepository.searchProducts(search, category, brand, pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setDescription2(updatedProduct.getDescription2());
        product.setCapacity(updatedProduct.getCapacity());
        product.setPrice(updatedProduct.getPrice());
        product.setBrand(updatedProduct.getBrand());
        product.setCategory(updatedProduct.getCategory());
        product.setWarranty(updatedProduct.getWarranty());
        product.setResponsiblePerson(updatedProduct.getResponsiblePerson());

        if (updatedProduct.getImageUrl1() != null) product.setImageUrl1(updatedProduct.getImageUrl1());
        if (updatedProduct.getImageUrl2() != null) product.setImageUrl2(updatedProduct.getImageUrl2());
        if (updatedProduct.getImageUrl3() != null) product.setImageUrl3(updatedProduct.getImageUrl3());
        if (updatedProduct.getImageUrl4() != null) product.setImageUrl4(updatedProduct.getImageUrl4());
        if (updatedProduct.getImageUrl5() != null) product.setImageUrl5(updatedProduct.getImageUrl5());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}