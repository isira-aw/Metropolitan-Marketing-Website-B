package com.marketing.service;

import com.marketing.dto.request.DivisionRequest;
import com.marketing.dto.response.DivisionResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.entity.Division;
import com.marketing.mapper.DivisionMapper;
import com.marketing.repository.DivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DivisionService {

    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;

    // Get all active divisions (for public)
    public List<DivisionResponse> getAllActiveDivisions() {
        return divisionRepository.findByStatusOrderByDisplayOrderAsc("active")
                .stream()
                .map(divisionMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get all divisions (for admin)
    public List<DivisionResponse> getAllDivisions() {
        return divisionRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(divisionMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get paginated divisions
    public PageResponse<DivisionResponse> getAllDivisionsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Division> divisionPage = divisionRepository.findAllByOrderByDisplayOrderAsc(pageable);
        return mapToPageResponse(divisionPage);
    }

    // Get paginated active divisions
    public PageResponse<DivisionResponse> getActiveDivisionsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Division> divisionPage = divisionRepository.findByStatusOrderByDisplayOrderAsc("active", pageable);
        return mapToPageResponse(divisionPage);
    }

    // Get single division by ID
    public DivisionResponse getDivisionById(String id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Division not found with id: " + id));
        return divisionMapper.toResponse(division);
    }

    // Get division by slug
    public DivisionResponse getDivisionBySlug(String slug) {
        Division division = divisionRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Division not found with slug: " + slug));
        return divisionMapper.toResponse(division);
    }

    // Create new division
    @Transactional
    public DivisionResponse createDivision(DivisionRequest request) {
        // Check if ID already exists
        if (divisionRepository.existsByDivisionsId(request.getDivisionsId())) {
            throw new RuntimeException("Division with ID '" + request.getDivisionsId() + "' already exists");
        }

        // Check if slug already exists
        if (divisionRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Division with slug '" + request.getSlug() + "' already exists");
        }

        Division division = divisionMapper.toEntity(request);
        Division savedDivision = divisionRepository.save(division);
        return divisionMapper.toResponse(savedDivision);
    }

    // Update existing division
    @Transactional
    public DivisionResponse updateDivision(String id, DivisionRequest request) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Division not found with id: " + id));

        // Check if slug already exists for another division
        if (divisionRepository.existsBySlugAndDivisionsIdNot(request.getSlug(), id)) {
            throw new RuntimeException("Division with slug '" + request.getSlug() + "' already exists");
        }

        divisionMapper.updateEntity(division, request);
        Division updatedDivision = divisionRepository.save(division);
        return divisionMapper.toResponse(updatedDivision);
    }

    // Delete division
    @Transactional
    public void deleteDivision(String id) {
        if (!divisionRepository.existsById(id)) {
            throw new RuntimeException("Division not found with id: " + id);
        }
        divisionRepository.deleteById(id);
    }

    // Toggle status
    @Transactional
    public DivisionResponse toggleStatus(String id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Division not found with id: " + id));
        division.setStatus(division.getStatus().equals("active") ? "inactive" : "active");
        Division updatedDivision = divisionRepository.save(division);
        return divisionMapper.toResponse(updatedDivision);
    }

    // Reorder divisions
    @Transactional
    public void reorderDivisions(List<String> divisionIds) {
        if (divisionIds == null || divisionIds.isEmpty()) {
            throw new IllegalArgumentException("Division IDs list cannot be null or empty");
        }

        for (int i = 0; i < divisionIds.size(); i++) {
            String divisionId = divisionIds.get(i);
            Division division = divisionRepository.findById(divisionId)
                    .orElseThrow(() -> new RuntimeException("Division not found with id: " + divisionId));
            division.setDisplayOrder(i);
            divisionRepository.save(division);
        }
    }

    // Helper method to map Page to PageResponse
    private PageResponse<DivisionResponse> mapToPageResponse(Page<Division> page) {
        List<DivisionResponse> content = page.getContent()
                .stream()
                .map(divisionMapper::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<DivisionResponse>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .first(page.isFirst())
                .empty(page.isEmpty())
                .build();
    }
}