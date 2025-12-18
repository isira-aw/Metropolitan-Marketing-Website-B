package com.marketing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String description2;
    private String capacity;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
