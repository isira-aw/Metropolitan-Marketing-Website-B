package com.marketing.service;

import com.marketing.dto.request.AboutUsRequestDTO;
import com.marketing.dto.response.AboutUsResponseDTO;
import com.marketing.entity.AboutUs;
import com.marketing.repository.AboutUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutUsService {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    // GET (Public + Admin)
    public AboutUsResponseDTO getAboutUs() {
        AboutUs aboutUs = aboutUsRepository.findAll()
                .stream()
                .findFirst()
                .orElse(new AboutUs());

        return mapToResponseDTO(aboutUs);
    }

    // UPDATE (Admin only)
    public AboutUsResponseDTO updateAboutUs(AboutUsRequestDTO dto) {

        AboutUs existing = aboutUsRepository.findAll()
                .stream()
                .findFirst()
                .orElse(new AboutUs());

        existing.setCompanyName(dto.getCompanyName());
        existing.setCompanyDescription(dto.getCompanyDescription());
        existing.setOwnerName(dto.getOwnerName());
        existing.setOwnerTitle(dto.getOwnerTitle());
        existing.setOwnerDescription(dto.getOwnerDescription());
        existing.setOwnerImageUrl(dto.getOwnerImageUrl());
        existing.setIntroduction(dto.getIntroduction());

        // JSON fields
        existing.setManagementTeamJson(dto.getManagementTeamJson());
        existing.setMilestonesJson(dto.getMilestonesJson());

        AboutUs saved = aboutUsRepository.save(existing);

        return mapToResponseDTO(saved);
    }

    // Mapper
    private AboutUsResponseDTO mapToResponseDTO(AboutUs aboutUs) {
        return new AboutUsResponseDTO(
                aboutUs.getId(),
                aboutUs.getCompanyName(),
                aboutUs.getCompanyDescription(),
                aboutUs.getOwnerName(),
                aboutUs.getOwnerTitle(),
                aboutUs.getOwnerDescription(),
                aboutUs.getOwnerImageUrl(),
                aboutUs.getIntroduction(),
                aboutUs.getManagementTeamJson(),
                aboutUs.getMilestonesJson(),
                aboutUs.getUpdatedAt()
        );
    }
}
