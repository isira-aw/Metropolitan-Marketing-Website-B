package com.marketing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    
    private String phone;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "facebook_url")
    private String facebookUrl;
    
    @Column(name = "twitter_url")
    private String twitterUrl;
    
    @Column(name = "instagram_url")
    private String instagramUrl;
    
    @Column(name = "linkedin_url")
    private String linkedinUrl;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
