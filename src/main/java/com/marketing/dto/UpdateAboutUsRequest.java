package com.marketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAboutUsRequest {

    private String companyName;
    private String companyDescription;
    private String ownerName;
    private String ownerTitle;
    private String ownerDescription;
    private String ownerImageUrl;
}
