package com.marketing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DivisionRequest {

    @NotBlank(message = "Divisions ID is required")
    private String divisionsId;

    @NotBlank(message = "Divisions name is required")
    private String divisionsName;

    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must be lowercase with hyphens only")
    private String slug;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(active|inactive)$", message = "Status must be 'active' or 'inactive'")
    private String status;

    private Integer displayOrder;

    // Nested structures
    private BasicInfo basicInfo;
    private List<SubDivision> subDivisions;
    private ContactUs contactUs;
}