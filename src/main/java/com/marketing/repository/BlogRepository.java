package com.marketing.repository;

import com.marketing.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Find by slug
    Optional<Blog> findBySlug(String slug);

    // Find all published blogs ordered by date descending
    Page<Blog> findByPublishedTrueOrderByDateDescCreatedAtDesc(Pageable pageable);

    // Find all blogs (admin) ordered by date descending
    Page<Blog> findAllByOrderByDateDescCreatedAtDesc(Pageable pageable);

    // Filter by division
    Page<Blog> findByDivisionAndPublishedTrueOrderByDateDescCreatedAtDesc(String division, Pageable pageable);

    // Filter by division (admin)
    Page<Blog> findByDivisionOrderByDateDescCreatedAtDesc(String division, Pageable pageable);

    // Search by topic or description (published)
    @Query("SELECT b FROM Blog b WHERE b.published = true AND " +
            "(LOWER(b.topic) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.paragraph) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY b.date DESC, b.createdAt DESC")
    Page<Blog> searchPublishedBlogs(@Param("keyword") String keyword, Pageable pageable);

    // Search by topic or description (admin)
    @Query("SELECT b FROM Blog b WHERE " +
            "LOWER(b.topic) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.paragraph) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY b.date DESC, b.createdAt DESC")
    Page<Blog> searchAllBlogs(@Param("keyword") String keyword, Pageable pageable);

    // Filter by division AND search (published)
    @Query("SELECT b FROM Blog b WHERE b.published = true AND b.division = :division AND " +
            "(LOWER(b.topic) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.paragraph) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY b.date DESC, b.createdAt DESC")
    Page<Blog> searchPublishedBlogsByDivision(@Param("division") String division,
                                              @Param("keyword") String keyword,
                                              Pageable pageable);

    // Filter by division AND search (admin)
    @Query("SELECT b FROM Blog b WHERE b.division = :division AND " +
            "(LOWER(b.topic) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.paragraph) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY b.date DESC, b.createdAt DESC")
    Page<Blog> searchAllBlogsByDivision(@Param("division") String division,
                                        @Param("keyword") String keyword,
                                        Pageable pageable);

    // Filter by date range (published)
    Page<Blog> findByPublishedTrueAndDateBetweenOrderByDateDescCreatedAtDesc(
            LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Filter by date range (admin)
    Page<Blog> findByDateBetweenOrderByDateDescCreatedAtDesc(
            LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Get recent blogs (published)
    List<Blog> findTop10ByPublishedTrueOrderByDateDescCreatedAtDesc();

    // Check if slug exists
    boolean existsBySlug(String slug);

    // Check if slug exists excluding specific ID
    boolean existsBySlugAndBlogIdNot(String slug, Long blogId);

    // Get all divisions (distinct)
    @Query("SELECT DISTINCT b.division FROM Blog b ORDER BY b.division")
    List<String> findAllDistinctDivisions();
}