package com.marketing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutUsResponse {

    private Long id;
    private String companyName;
    private String companyDescription;
    private String ownerName;
    private String ownerTitle;
    private String ownerDescription;
    private String ownerImageUrl;
    private LocalDateTime updatedAt;
}
