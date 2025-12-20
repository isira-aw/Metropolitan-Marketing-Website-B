package com.marketing.mapper;

import com.marketing.dto.request.NewsRequest;
import com.marketing.dto.response.NewsResponse;
import com.marketing.entity.NewsItem;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

    public NewsItem toEntity(NewsRequest request) {
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(request.getTitle());
        newsItem.setImageUrl(request.getImageUrl());
        newsItem.setThumbnailUrl(request.getThumbnailUrl());
        newsItem.setDescription(request.getDescription());
        newsItem.setContent(request.getContent());
        newsItem.setCategory(request.getCategory());
        newsItem.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
        newsItem.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        newsItem.setIsPublished(request.getIsPublished() != null ? request.getIsPublished() : true);
        newsItem.setExpireDate(request.getExpireDate());
        newsItem.setAuthor(request.getAuthor());
        newsItem.setViewCount(0L);
        return newsItem;
    }

    public NewsResponse toResponse(NewsItem newsItem) {
        return NewsResponse.builder()
                .id(newsItem.getId())
                .title(newsItem.getTitle())
                .imageUrl(newsItem.getImageUrl())
                .thumbnailUrl(newsItem.getThumbnailUrl())
                .description(newsItem.getDescription())
                .content(newsItem.getContent())
                .category(newsItem.getCategory())
                .displayOrder(newsItem.getDisplayOrder())
                .isFeatured(newsItem.getIsFeatured())
                .isPublished(newsItem.getIsPublished())
                .expireDate(newsItem.getExpireDate())
                .createdAt(newsItem.getCreatedAt())
                .updatedAt(newsItem.getUpdatedAt())
                .author(newsItem.getAuthor())
                .viewCount(newsItem.getViewCount())
                .build();
    }

    public void updateEntity(NewsItem newsItem, NewsRequest request) {
        newsItem.setTitle(request.getTitle());
        newsItem.setDescription(request.getDescription());
        newsItem.setContent(request.getContent());
        newsItem.setCategory(request.getCategory());

        if (request.getImageUrl() != null) {
            newsItem.setImageUrl(request.getImageUrl());
        }
        if (request.getThumbnailUrl() != null) {
            newsItem.setThumbnailUrl(request.getThumbnailUrl());
        }
        if (request.getDisplayOrder() != null) {
            newsItem.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getIsFeatured() != null) {
            newsItem.setIsFeatured(request.getIsFeatured());
        }
        if (request.getIsPublished() != null) {
            newsItem.setIsPublished(request.getIsPublished());
        }
        if (request.getExpireDate() != null) {
            newsItem.setExpireDate(request.getExpireDate());
        }
        if (request.getAuthor() != null) {
            newsItem.setAuthor(request.getAuthor());
        }
    }
}