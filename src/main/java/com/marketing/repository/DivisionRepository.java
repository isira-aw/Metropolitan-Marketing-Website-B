package com.marketing.repository;

import com.marketing.entity.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {

    // Find all active divisions ordered by display order
    List<Division> findByStatusOrderByDisplayOrderAsc(String status);

    // Find all divisions ordered by display order
    List<Division> findAllByOrderByDisplayOrderAsc();

    // Find by slug
    Optional<Division> findBySlug(String slug);

    // Pagination - all divisions
    Page<Division> findAllByOrderByDisplayOrderAsc(Pageable pageable);

    // Pagination - active divisions only
    Page<Division> findByStatusOrderByDisplayOrderAsc(String status, Pageable pageable);

    // Check if slug exists
    boolean existsBySlug(String slug);

    // Check if slug exists excluding specific ID (for updates)
    boolean existsBySlugAndDivisionsIdNot(String slug, String divisionsId);

    // Check if divisions_id exists
    boolean existsByDivisionsId(String divisionsId);
}