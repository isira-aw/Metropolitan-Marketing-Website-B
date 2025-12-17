package com.marketing.service;

import com.marketing.entity.AboutUs;
import com.marketing.repository.AboutUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutUsService {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    public AboutUs getAboutUs() {
        return aboutUsRepository.findAll().stream()
                .findFirst()
                .orElse(new AboutUs());
    }

    public AboutUs updateAboutUs(AboutUs aboutUs) {
        AboutUs existing = aboutUsRepository.findAll().stream()
                .findFirst()
                .orElse(new AboutUs());
        
        if (existing.getId() != null) {
            aboutUs.setId(existing.getId());
        }
        
        return aboutUsRepository.save(aboutUs);
    }
}
