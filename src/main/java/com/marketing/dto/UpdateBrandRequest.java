package com.marketing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBrandRequest {

    @NotBlank(message = "Brand name is required")
    @Size(min = 1, max = 255, message = "Brand name must be between 1 and 255 characters")
    private String name;
}
