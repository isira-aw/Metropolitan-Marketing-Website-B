package com.marketing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DivisionResponse {

    private String divisionsId;
    private String divisionsName;
    private String slug;
    private String status;
    private Integer displayOrder;

    // Parsed JSON objects
    private Object basicInfo;
    private Object subDivisions;
    private Object contactUs;

    // Meta
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}