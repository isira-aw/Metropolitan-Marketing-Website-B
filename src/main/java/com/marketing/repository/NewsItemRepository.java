package com.marketing.repository;

import com.marketing.entity.NewsCategory;
import com.marketing.entity.NewsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {

    // Find all ordered by display order
    List<NewsItem> findAllByOrderByDisplayOrderAsc();

    // Find by category with ordering
    List<NewsItem> findByCategoryOrderByDisplayOrderAsc(NewsCategory category);

    // Pagination - all items
    Page<NewsItem> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Pagination - published items only
    Page<NewsItem> findByIsPublishedTrueOrderByCreatedAtDesc(Pageable pageable);

    // Pagination - by category
    Page<NewsItem> findByCategoryOrderByCreatedAtDesc(NewsCategory category, Pageable pageable);

    // Pagination - published by category
    Page<NewsItem> findByCategoryAndIsPublishedTrueOrderByCreatedAtDesc(NewsCategory category, Pageable pageable);

    // Featured news
    List<NewsItem> findByIsFeaturedTrueAndIsPublishedTrueOrderByCreatedAtDesc();

    // Search by title or description
    @Query("SELECT n FROM NewsItem n WHERE " +
            "(LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY n.createdAt DESC")
    Page<NewsItem> searchByKeyword(String keyword, Pageable pageable);

    // Active news (not expired)
    @Query("SELECT n FROM NewsItem n WHERE " +
            "n.isPublished = true AND " +
            "(n.expireDate IS NULL OR n.expireDate > :now) " +
            "ORDER BY n.createdAt DESC")
    Page<NewsItem> findActiveNews(LocalDateTime now, Pageable pageable);
}