package com.marketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
class ResponsiblePerson {
    private String profileImage;
    private String name;
    private String designation;
    private String contactNumber;
    private String email;
    private String whatsAppNumber;
    private String vCard;
}