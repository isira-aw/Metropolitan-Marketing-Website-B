package com.marketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "home_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "welcome_message", columnDefinition = "TEXT")
    private String welcomeMessage;

    @Column(name = "short_paragraph", columnDefinition = "TEXT")
    private String shortParagraph;

    @Column(name = "our_brands_json", columnDefinition = "TEXT")
    private String ourBrandsJson;

    @Column(name = "our_customers_json", columnDefinition = "TEXT")
    private String ourCustomersJson;

    @Column(name = "our_platforms_json", columnDefinition = "TEXT")
    private String ourPlatformsJson;

    @Column(name = "recommendations_json", columnDefinition = "TEXT")
    private String recommendationsJson;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}