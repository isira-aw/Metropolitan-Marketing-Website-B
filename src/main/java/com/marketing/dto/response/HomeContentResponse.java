package com.marketing.dto.response;

import com.marketing.dto.request.BrandItemDto;
import com.marketing.dto.request.CustomerItemDto;
import com.marketing.dto.request.PlatformItemDto;
import com.marketing.dto.request.RecommendationItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeContentResponse {
    private Long id;
    private String welcomeMessage;
    private String shortParagraph;
    private List<BrandItemDto> ourBrands;
    private List<CustomerItemDto> ourCustomers;
    private List<PlatformItemDto> ourPlatforms;
    private List<RecommendationItemDto> recommendations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}