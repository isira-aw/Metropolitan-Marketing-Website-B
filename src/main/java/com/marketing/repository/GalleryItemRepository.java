package com.marketing.repository;

import com.marketing.entity.GalleryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryItemRepository extends JpaRepository<GalleryItem, Long> {
    List<GalleryItem> findAllByOrderByDisplayOrderAsc();
    List<GalleryItem> findByCategoryOrderByDisplayOrderAsc(String category);
}
