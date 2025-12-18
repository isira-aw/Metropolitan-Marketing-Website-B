package com.marketing.service;

import com.marketing.dto.*;
import com.marketing.entity.AboutUs;
import com.marketing.mapper.AboutUsMapper;
import com.marketing.repository.AboutUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutUsService {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private AboutUsMapper aboutUsMapper;

    public AboutUsResponse getAboutUs() {
        AboutUs aboutUs = aboutUsRepository.findAll().stream()
                .findFirst()
                .orElse(new AboutUs());
        return aboutUsMapper.toResponse(aboutUs);
    }

    public AboutUsResponse updateAboutUs(UpdateAboutUsRequest request) {
        AboutUs existing = aboutUsRepository.findAll().stream()
                .findFirst()
                .orElse(new AboutUs());

        aboutUsMapper.updateEntity(existing, request);
        AboutUs updated = aboutUsRepository.save(existing);
        return aboutUsMapper.toResponse(updated);
    }
}
