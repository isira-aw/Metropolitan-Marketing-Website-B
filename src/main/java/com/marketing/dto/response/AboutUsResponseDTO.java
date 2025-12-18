package com.marketing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AboutUsResponseDTO {

    private Long id;

    private String companyName;
    private String companyDescription;

    private String ownerName;
    private String ownerTitle;
    private String ownerDescription;
    private String ownerImageUrl;

    private String introduction;

    // JSON strings
    private String managementTeamJson;
    private String milestonesJson;

    private LocalDateTime updatedAt;
}
