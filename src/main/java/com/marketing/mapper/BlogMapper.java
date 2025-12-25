package com.marketing.mapper;

import com.marketing.dto.request.BlogRequest;
import com.marketing.dto.response.BlogResponse;
import com.marketing.entity.Blog;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {

    public Blog toEntity(BlogRequest request) {
        Blog blog = new Blog();
        blog.setTopic(request.getTopic());
        blog.setDate(request.getDate());
        blog.setDivision(request.getDivision());
        blog.setImageUrl(request.getImageUrl());
        blog.setShortDescription(request.getShortDescription());
        blog.setParagraph(request.getParagraph());
        blog.setSlug(request.getSlug());
        blog.setPublished(request.getPublished() != null ? request.getPublished() : true);
        blog.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
        return blog;
    }

    public BlogResponse toResponse(Blog blog) {
        return BlogResponse.builder()
                .blogId(blog.getBlogId())
                .topic(blog.getTopic())
                .date(blog.getDate())
                .division(blog.getDivision())
                .imageUrl(blog.getImageUrl())
                .shortDescription(blog.getShortDescription())
                .paragraph(blog.getParagraph())
                .slug(blog.getSlug())
                .published(blog.getPublished())
                .viewCount(blog.getViewCount())
                .displayOrder(blog.getDisplayOrder())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
    }

    public void updateEntity(Blog blog, BlogRequest request) {
        blog.setTopic(request.getTopic());
        blog.setDate(request.getDate());
        blog.setDivision(request.getDivision());
        blog.setImageUrl(request.getImageUrl());
        blog.setShortDescription(request.getShortDescription());
        blog.setParagraph(request.getParagraph());

        if (request.getSlug() != null && !request.getSlug().isEmpty()) {
            blog.setSlug(request.getSlug());
        }

        if (request.getPublished() != null) {
            blog.setPublished(request.getPublished());
        }

        if (request.getDisplayOrder() != null) {
            blog.setDisplayOrder(request.getDisplayOrder());
        }
    }
}