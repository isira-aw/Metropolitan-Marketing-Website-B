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
class SubDivision {
    private String subDivisionsName;
    private String simpleDivisions;
    private List<String> keyFeatures;
    private List<Partner> globalPartners;
    private List<Partner> brands;
    private List<Section> sections;
    private List<ResponsiblePerson> responsiblePersons;
}
