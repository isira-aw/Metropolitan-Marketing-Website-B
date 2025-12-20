package com.marketing.repository;

import com.marketing.entity.HomeContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeContentRepository extends JpaRepository<HomeContent, Long> {
    Optional<HomeContent> findFirstByOrderByIdAsc();
}