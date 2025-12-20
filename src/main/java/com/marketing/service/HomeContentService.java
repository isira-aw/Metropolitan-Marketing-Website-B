package com.marketing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketing.dto.request.*;
import com.marketing.dto.response.HomeContentResponse;
import com.marketing.entity.HomeContent;
import com.marketing.repository.HomeContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeContentService {

    @Autowired
    private HomeContentRepository homeContentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Get home content - returns null data if not exists
     */
    @Transactional(readOnly = true)
    public HomeContentResponse getHomeContent() {
        return homeContentRepository.findFirstByOrderByIdAsc()
                .map(this::mapToResponse)
                .orElseGet(this::getDefaultResponse);
    }

    /**
     * Update home content - creates if not exists
     */
    @Transactional
    public HomeContentResponse updateHomeContent(HomeContentRequest request) {
        HomeContent content = homeContentRepository.findFirstByOrderByIdAsc()
                .orElse(new HomeContent());

        content.setWelcomeMessage(request.getWelcomeMessage());
        content.setShortParagraph(request.getShortParagraph());

        // Serialize lists to JSON
        try {
            content.setOurBrandsJson(objectMapper.writeValueAsString(
                    request.getOurBrands() != null ? request.getOurBrands() : new ArrayList<>()
            ));
            content.setOurCustomersJson(objectMapper.writeValueAsString(
                    request.getOurCustomers() != null ? request.getOurCustomers() : new ArrayList<>()
            ));
            content.setOurPlatformsJson(objectMapper.writeValueAsString(
                    request.getOurPlatforms() != null ? request.getOurPlatforms() : new ArrayList<>()
            ));
            content.setRecommendationsJson(objectMapper.writeValueAsString(
                    request.getRecommendations() != null ? request.getRecommendations() : new ArrayList<>()
            ));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize home content data", e);
        }

        content = homeContentRepository.save(content);
        return mapToResponse(content);
    }

    /**
     * Initialize home content with default values (call this separately)
     */
    @Transactional
    public HomeContentResponse initializeHomeContent() {
        // Check if already exists
        if (homeContentRepository.findFirstByOrderByIdAsc().isPresent()) {
            return getHomeContent();
        }

        HomeContent content = new HomeContent();
        content.setWelcomeMessage("Welcome to our platform");
        content.setShortParagraph("We provide innovative marketing solutions");
        content.setOurBrandsJson("[]");
        content.setOurCustomersJson("[]");
        content.setOurPlatformsJson("[]");
        content.setRecommendationsJson("[]");

        content = homeContentRepository.save(content);
        return mapToResponse(content);
    }

    /**
     * Get default response when no data exists
     */
    private HomeContentResponse getDefaultResponse() {
        HomeContentResponse response = new HomeContentResponse();
        response.setId(0L);
        response.setWelcomeMessage("");
        response.setShortParagraph("");
        response.setOurBrands(new ArrayList<>());
        response.setOurCustomers(new ArrayList<>());
        response.setOurPlatforms(new ArrayList<>());
        response.setRecommendations(new ArrayList<>());
        return response;
    }

    /**
     * Map entity to response DTO
     */
    private HomeContentResponse mapToResponse(HomeContent content) {
        HomeContentResponse response = new HomeContentResponse();
        response.setId(content.getId());
        response.setWelcomeMessage(content.getWelcomeMessage());
        response.setShortParagraph(content.getShortParagraph());
        response.setCreatedAt(content.getCreatedAt());
        response.setUpdatedAt(content.getUpdatedAt());

        // Deserialize JSON to lists
        try {
            response.setOurBrands(objectMapper.readValue(
                    content.getOurBrandsJson() != null ? content.getOurBrandsJson() : "[]",
                    new TypeReference<List<BrandItemDto>>() {}
            ));
            response.setOurCustomers(objectMapper.readValue(
                    content.getOurCustomersJson() != null ? content.getOurCustomersJson() : "[]",
                    new TypeReference<List<CustomerItemDto>>() {}
            ));
            response.setOurPlatforms(objectMapper.readValue(
                    content.getOurPlatformsJson() != null ? content.getOurPlatformsJson() : "[]",
                    new TypeReference<List<PlatformItemDto>>() {}
            ));
            response.setRecommendations(objectMapper.readValue(
                    content.getRecommendationsJson() != null ? content.getRecommendationsJson() : "[]",
                    new TypeReference<List<RecommendationItemDto>>() {}
            ));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize home content data", e);
        }

        return response;
    }
}