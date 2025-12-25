package com.marketing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Division is required")
    private String division; // CentralAC, Elevator, Fire, Generator, Solar, ELV

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotBlank(message = "Short description is required")
    private String shortDescription;

    @NotBlank(message = "Paragraph is required")
    private String paragraph;

    private String slug;

    private Boolean published;

    private Integer displayOrder;
}