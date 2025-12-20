package com.marketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationItemDto {
    private String imageUrl;
    private String link;
    private String name;
    private String message;
    private Integer rating;
}