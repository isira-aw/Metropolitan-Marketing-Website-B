package com.marketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "divisions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Division {

    @Id
    @Column(name = "divisions_id")
    private String divisionsId; // e.g., "IND_DISPLAY_001"

    @Column(name = "divisions_name", nullable = false)
    private String divisionsName;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String status = "active"; // active, inactive

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    // JSON columns stored as TEXT
    @Column(name = "basic_info", columnDefinition = "TEXT")
    private String basicInfo; // JSON object

    @Column(name = "sub_divisions", columnDefinition = "TEXT")
    private String subDivisions; // JSON array

    @Column(name = "contact_us", columnDefinition = "TEXT")
    private String contactUs; // JSON object

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}