package com.marketing.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters")
    private String name;

    private String description;

    private String description2;

    private String capacity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    private String brand;

    private String category;

    private String warranty;

    private String responsiblePerson;

    private String imageUrl1;

    private String imageUrl2;

    private String imageUrl3;

    private String imageUrl4;

    private String imageUrl5;
}
