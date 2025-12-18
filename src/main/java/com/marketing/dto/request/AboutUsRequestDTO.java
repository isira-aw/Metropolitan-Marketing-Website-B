package com.marketing.dto.request;

import lombok.Data;

@Data
public class AboutUsRequestDTO {

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
}
