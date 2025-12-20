package com.marketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    private String name;
    private String imageUrl;
    private String link;
    private String description;
    private Boolean isActive;
}