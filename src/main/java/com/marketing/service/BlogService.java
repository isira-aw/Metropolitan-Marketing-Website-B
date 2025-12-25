package com.marketing.service;

import com.marketing.dto.request.BlogRequest;
import com.marketing.dto.response.BlogResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.entity.Blog;
import com.marketing.mapper.BlogMapper;
import com.marketing.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    // ============ PUBLIC METHODS ============

    /**
     * Get all published blogs with pagination
     */
    public PageResponse<BlogResponse> getAllPublishedBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.findByPublishedTrueOrderByDateDescCreatedAtDesc(pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Get published blogs filtered by division
     */
    public PageResponse<BlogResponse> getPublishedBlogsByDivision(String division, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.findByDivisionAndPublishedTrueOrderByDateDescCreatedAtDesc(division, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Search published blogs by keyword
     */
    public PageResponse<BlogResponse> searchPublishedBlogs(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.searchPublishedBlogs(keyword, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Search published blogs by division AND keyword
     */
    public PageResponse<BlogResponse> searchPublishedBlogsByDivision(String division, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.searchPublishedBlogsByDivision(division, keyword, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Get published blogs by date range
     */
    public PageResponse<BlogResponse> getPublishedBlogsByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.findByPublishedTrueAndDateBetweenOrderByDateDescCreatedAtDesc(startDate, endDate, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Get single blog by ID (increments view count)
     */
    @Transactional
    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        // Increment view count
        blog.setViewCount(blog.getViewCount() + 1);
        blogRepository.save(blog);

        return blogMapper.toResponse(blog);
    }

    /**
     * Get single blog by slug (increments view count)
     */
    @Transactional
    public BlogResponse getBlogBySlug(String slug) {
        Blog blog = blogRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Blog not found with slug: " + slug));

        // Increment view count
        blog.setViewCount(blog.getViewCount() + 1);
        blogRepository.save(blog);

        return blogMapper.toResponse(blog);
    }

    /**
     * Get recent published blogs (top 10)
     */
    public List<BlogResponse> getRecentPublishedBlogs() {
        return blogRepository.findTop10ByPublishedTrueOrderByDateDescCreatedAtDesc()
                .stream()
                .map(blogMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ============ ADMIN METHODS ============

    /**
     * Get all blogs (admin) with pagination
     */
    public PageResponse<BlogResponse> getAllBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.findAllByOrderByDateDescCreatedAtDesc(pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Get all blogs filtered by division (admin)
     */
    public PageResponse<BlogResponse> getBlogsByDivision(String division, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.findByDivisionOrderByDateDescCreatedAtDesc(division, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Search all blogs by keyword (admin)
     */
    public PageResponse<BlogResponse> searchAllBlogs(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.searchAllBlogs(keyword, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Search all blogs by division AND keyword (admin)
     */
    public PageResponse<BlogResponse> searchBlogsByDivision(String division, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.searchAllBlogsByDivision(division, keyword, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Get blogs by date range (admin)
     */
    public PageResponse<BlogResponse> getBlogsByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogRepository.findByDateBetweenOrderByDateDescCreatedAtDesc(startDate, endDate, pageable);
        return mapToPageResponse(blogPage);
    }

    /**
     * Get all distinct divisions
     */
    public List<String> getAllDivisions() {
        return blogRepository.findAllDistinctDivisions();
    }

    /**
     * Create new blog
     */
    @Transactional
    public BlogResponse createBlog(BlogRequest request) {
        // Check if slug already exists
        if (request.getSlug() != null && blogRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Blog with slug '" + request.getSlug() + "' already exists");
        }

        Blog blog = blogMapper.toEntity(request);
        Blog savedBlog = blogRepository.save(blog);
        return blogMapper.toResponse(savedBlog);
    }

    /**
     * Update existing blog
     */
    @Transactional
    public BlogResponse updateBlog(Long id, BlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        // Check if slug already exists for another blog
        if (request.getSlug() != null && blogRepository.existsBySlugAndBlogIdNot(request.getSlug(), id)) {
            throw new RuntimeException("Blog with slug '" + request.getSlug() + "' already exists");
        }

        blogMapper.updateEntity(blog, request);
        Blog updatedBlog = blogRepository.save(blog);
        return blogMapper.toResponse(updatedBlog);
    }

    /**
     * Delete blog
     */
    @Transactional
    public void deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new RuntimeException("Blog not found with id: " + id);
        }
        blogRepository.deleteById(id);
    }

    /**
     * Toggle publish status
     */
    @Transactional
    public BlogResponse togglePublishStatus(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
        blog.setPublished(!blog.getPublished());
        Blog updatedBlog = blogRepository.save(blog);
        return blogMapper.toResponse(updatedBlog);
    }

    // ============ HELPER METHODS ============

    private PageResponse<BlogResponse> mapToPageResponse(Page<Blog> page) {
        List<BlogResponse> content = page.getContent()
                .stream()
                .map(blogMapper::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<BlogResponse>builder()
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