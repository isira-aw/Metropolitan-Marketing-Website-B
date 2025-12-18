package com.marketing.mapper;

import com.marketing.dto.*;
import com.marketing.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .description2(product.getDescription2())
                .capacity(product.getCapacity())
                .price(product.getPrice())
                .brand(product.getBrand())
                .category(product.getCategory())
                .warranty(product.getWarranty())
                .responsiblePerson(product.getResponsiblePerson())
                .imageUrl1(product.getImageUrl1())
                .imageUrl2(product.getImageUrl2())
                .imageUrl3(product.getImageUrl3())
                .imageUrl4(product.getImageUrl4())
                .imageUrl5(product.getImageUrl5())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductListResponse toProductListResponse(Page<Product> productPage) {
        return ProductListResponse.builder()
                .products(toResponseList(productPage.getContent()))
                .currentPage(productPage.getNumber())
                .totalPages(productPage.getTotalPages())
                .totalItems(productPage.getTotalElements())
                .pageSize(productPage.getSize())
                .hasNext(productPage.hasNext())
                .hasPrevious(productPage.hasPrevious())
                .build();
    }

    public PageResponse<ProductResponse> toPageResponse(Page<Product> productPage) {
        return PageResponse.<ProductResponse>builder()
                .content(toResponseList(productPage.getContent()))
                .currentPage(productPage.getNumber())
                .totalPages(productPage.getTotalPages())
                .totalItems(productPage.getTotalElements())
                .pageSize(productPage.getSize())
                .hasNext(productPage.hasNext())
                .hasPrevious(productPage.hasPrevious())
                .build();
    }

    public Product toEntity(CreateProductRequest request) {
        if (request == null) {
            return null;
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setDescription2(request.getDescription2());
        product.setCapacity(request.getCapacity());
        product.setPrice(request.getPrice());
        product.setBrand(request.getBrand());
        product.setCategory(request.getCategory());
        product.setWarranty(request.getWarranty());
        product.setResponsiblePerson(request.getResponsiblePerson());
        product.setImageUrl1(request.getImageUrl1());
        product.setImageUrl2(request.getImageUrl2());
        product.setImageUrl3(request.getImageUrl3());
        product.setImageUrl4(request.getImageUrl4());
        product.setImageUrl5(request.getImageUrl5());

        return product;
    }

    public void updateEntity(Product product, UpdateProductRequest request) {
        if (request == null) {
            return;
        }

        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getDescription2() != null) {
            product.setDescription2(request.getDescription2());
        }
        if (request.getCapacity() != null) {
            product.setCapacity(request.getCapacity());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getBrand() != null) {
            product.setBrand(request.getBrand());
        }
        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }
        if (request.getWarranty() != null) {
            product.setWarranty(request.getWarranty());
        }
        if (request.getResponsiblePerson() != null) {
            product.setResponsiblePerson(request.getResponsiblePerson());
        }
        if (request.getImageUrl1() != null) {
            product.setImageUrl1(request.getImageUrl1());
        }
        if (request.getImageUrl2() != null) {
            product.setImageUrl2(request.getImageUrl2());
        }
        if (request.getImageUrl3() != null) {
            product.setImageUrl3(request.getImageUrl3());
        }
        if (request.getImageUrl4() != null) {
            product.setImageUrl4(request.getImageUrl4());
        }
        if (request.getImageUrl5() != null) {
            product.setImageUrl5(request.getImageUrl5());
        }
    }
}
