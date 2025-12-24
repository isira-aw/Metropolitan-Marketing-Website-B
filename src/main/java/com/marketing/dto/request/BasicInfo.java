package com.marketing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Nested DTOs
@Data
@NoArgsConstructor
@AllArgsConstructor
class BasicInfo {
    private String shortDescription;
    private String longDescription;
    private String bannerImage;
}
