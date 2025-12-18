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
public class AdminResponse {

    private Long id;
    private String username;
    private String email;
    private Boolean license;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
