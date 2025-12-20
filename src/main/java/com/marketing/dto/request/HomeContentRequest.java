package com.marketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeContentRequest {
    private String welcomeMessage;
    private String shortParagraph;
    private List<BrandItemDto> ourBrands;
    private List<CustomerItemDto> ourCustomers;
    private List<PlatformItemDto> ourPlatforms;
    private List<RecommendationItemDto> recommendations;
}