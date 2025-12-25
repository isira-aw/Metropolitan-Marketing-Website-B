package com.marketing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {

    private Long blogId;
    private String topic;
    private LocalDate date;
    private String division;
    private String imageUrl;
    private String shortDescription;
    private String paragraph;
    private String slug;
    private Boolean published;
    private Long viewCount;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}