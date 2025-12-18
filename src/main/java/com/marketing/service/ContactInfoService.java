package com.marketing.service;

import com.marketing.dto.*;
import com.marketing.entity.ContactInfo;
import com.marketing.mapper.ContactInfoMapper;
import com.marketing.repository.ContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoService {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    public ContactInfoResponse getContactInfo() {
        ContactInfo contactInfo = contactInfoRepository.findAll().stream()
                .findFirst()
                .orElse(new ContactInfo());
        return contactInfoMapper.toResponse(contactInfo);
    }

    public ContactInfoResponse updateContactInfo(UpdateContactInfoRequest request) {
        ContactInfo existing = contactInfoRepository.findAll().stream()
                .findFirst()
                .orElse(new ContactInfo());

        contactInfoMapper.updateEntity(existing, request);
        ContactInfo updated = contactInfoRepository.save(existing);
        return contactInfoMapper.toResponse(updated);
    }
}
