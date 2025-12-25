package com.marketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String division; // CentralAC, Elevator, Fire, Generator, Solar, ELV

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "short_description", nullable = false, columnDefinition = "TEXT")
    private String shortDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String paragraph;

    @Column(nullable = false)
    private String slug; // URL-friendly version of topic

    @Column(name = "is_published")
    private Boolean published = true;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Indexes for filtering and searching
    @PrePersist
    @PreUpdate
    private void generateSlug() {
        if (this.topic != null && (this.slug == null || this.slug.isEmpty())) {
            this.slug = this.topic.toLowerCase()
                    .replaceAll("[^a-z0-9\\s-]", "")
                    .replaceAll("\\s+", "-")
                    .replaceAll("-+", "-")
                    .trim();
        }
    }
}