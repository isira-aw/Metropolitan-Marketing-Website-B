package com.marketing.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketing.dto.request.DivisionRequest;
import com.marketing.dto.response.DivisionResponse;
import com.marketing.entity.Division;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DivisionMapper {

    private final ObjectMapper objectMapper;

    public Division toEntity(DivisionRequest request) {
        Division division = new Division();
        division.setDivisionsId(request.getDivisionsId());
        division.setDivisionsName(request.getDivisionsName());
        division.setSlug(request.getSlug());
        division.setStatus(request.getStatus());
        division.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        // Convert objects to JSON strings
        division.setBasicInfo(toJson(request.getBasicInfo()));
        division.setSubDivisions(toJson(request.getSubDivisions()));
        division.setContactUs(toJson(request.getContactUs()));

        return division;
    }

    public DivisionResponse toResponse(Division division) {
        return DivisionResponse.builder()
                .divisionsId(division.getDivisionsId())
                .divisionsName(division.getDivisionsName())
                .slug(division.getSlug())
                .status(division.getStatus())
                .displayOrder(division.getDisplayOrder())
                .basicInfo(fromJson(division.getBasicInfo()))
                .subDivisions(fromJson(division.getSubDivisions()))
                .contactUs(fromJson(division.getContactUs()))
                .createdAt(division.getCreatedAt())
                .updatedAt(division.getUpdatedAt())
                .build();
    }

    public void updateEntity(Division division, DivisionRequest request) {
        division.setDivisionsName(request.getDivisionsName());
        division.setSlug(request.getSlug());
        division.setStatus(request.getStatus());

        if (request.getDisplayOrder() != null) {
            division.setDisplayOrder(request.getDisplayOrder());
        }

        // Update JSON fields
        if (request.getBasicInfo() != null) {
            division.setBasicInfo(toJson(request.getBasicInfo()));
        }
        if (request.getSubDivisions() != null) {
            division.setSubDivisions(toJson(request.getSubDivisions()));
        }
        if (request.getContactUs() != null) {
            division.setContactUs(toJson(request.getContactUs()));
        }
    }

    private String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

    private Object fromJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, Object.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}