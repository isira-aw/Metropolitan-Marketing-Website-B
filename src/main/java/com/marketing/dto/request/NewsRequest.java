package com.marketing.dto.request;

import com.marketing.entity.NewsCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String thumbnailUrl;

    @NotBlank(message = "Description is required")
    private String description;

    private String content;

    @NotNull(message = "Category is required")
    private NewsCategory category;

    private Integer displayOrder;

    private Boolean isFeatured;

    private Boolean isPublished;

    private LocalDateTime expireDate;

    private String author;
}