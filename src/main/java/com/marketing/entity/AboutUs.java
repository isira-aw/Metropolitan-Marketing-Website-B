package com.marketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "about_us")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutUs {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "company_name")
    private String companyName;
    
    @Column(name = "company_description", columnDefinition = "TEXT")
    private String companyDescription;
    
    @Column(name = "owner_name")
    private String ownerName;
    
    @Column(name = "owner_title")
    private String ownerTitle;
    
    @Column(name = "owner_description", columnDefinition = "TEXT")
    private String ownerDescription;
    
    @Column(name = "owner_image_url")
    private String ownerImageUrl;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
