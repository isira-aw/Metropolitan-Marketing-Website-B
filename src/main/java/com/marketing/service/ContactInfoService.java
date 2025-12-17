package com.marketing.service;

import com.marketing.entity.ContactInfo;
import com.marketing.repository.ContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoService {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    public ContactInfo getContactInfo() {
        return contactInfoRepository.findAll().stream()
                .findFirst()
                .orElse(new ContactInfo());
    }

    public ContactInfo updateContactInfo(ContactInfo contactInfo) {
        ContactInfo existing = contactInfoRepository.findAll().stream()
                .findFirst()
                .orElse(new ContactInfo());
        
        if (existing.getId() != null) {
            contactInfo.setId(existing.getId());
        }
        
        return contactInfoRepository.save(contactInfo);
    }
}
