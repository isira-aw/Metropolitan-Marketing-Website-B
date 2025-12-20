package com.marketing.dto.response;

import com.marketing.entity.NewsCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsResponse {

    private Long id;
    private String title;
    private String imageUrl;
    private String thumbnailUrl;
    private String description;
    private String content;
    private NewsCategory category;
    private Integer displayOrder;
    private Boolean isFeatured;
    private Boolean isPublished;
    private LocalDateTime expireDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String author;
    private Long viewCount;
}