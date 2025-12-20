package com.marketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerItemDto {
    private String imageUrl;
    private String link;
    private String name;
}