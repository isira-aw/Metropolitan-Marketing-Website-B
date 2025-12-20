package com.marketing.service;

import com.marketing.dto.request.NewsRequest;
import com.marketing.dto.response.NewsResponse;
import com.marketing.dto.response.PageResponse;
import com.marketing.entity.NewsCategory;
import com.marketing.entity.NewsItem;
import com.marketing.mapper.NewsMapper;
import com.marketing.repository.NewsItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsItemRepository newsItemRepository;
    private final NewsMapper newsMapper;

    // Get all items without pagination
    public List<NewsResponse> getAllItems() {
        return newsItemRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(newsMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get paginated items
    public PageResponse<NewsResponse> getAllItemsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NewsItem> newsPage = newsItemRepository.findAllByOrderByCreatedAtDesc(pageable);
        return mapToPageResponse(newsPage);
    }

    // Get published items with pagination
    public PageResponse<NewsResponse> getPublishedItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NewsItem> newsPage = newsItemRepository.findByIsPublishedTrueOrderByCreatedAtDesc(pageable);
        return mapToPageResponse(newsPage);
    }

    // Get items by category with pagination
    public PageResponse<NewsResponse> getItemsByCategory(NewsCategory category, int page, int size, boolean publishedOnly) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NewsItem> newsPage;

        if (publishedOnly) {
            newsPage = newsItemRepository.findByCategoryAndIsPublishedTrueOrderByCreatedAtDesc(category, pageable);
        } else {
            newsPage = newsItemRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
        }

        return mapToPageResponse(newsPage);
    }

    // Search items
    public PageResponse<NewsResponse> searchItems(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NewsItem> newsPage = newsItemRepository.searchByKeyword(keyword, pageable);
        return mapToPageResponse(newsPage);
    }

    // Get active news (not expired)
    public PageResponse<NewsResponse> getActiveNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NewsItem> newsPage = newsItemRepository.findActiveNews(LocalDateTime.now(), pageable);
        return mapToPageResponse(newsPage);
    }

    // Get featured news
    public List<NewsResponse> getFeaturedNews() {
        return newsItemRepository.findByIsFeaturedTrueAndIsPublishedTrueOrderByCreatedAtDesc()
                .stream()
                .map(newsMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get single item by ID
    public NewsResponse getItemById(Long id) {
        NewsItem newsItem = newsItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News item not found with id: " + id));
        return newsMapper.toResponse(newsItem);
    }

    // Get single item and increment view count
    @Transactional
    public NewsResponse getItemByIdAndIncrementView(Long id) {
        NewsItem newsItem = newsItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News item not found with id: " + id));
        newsItem.setViewCount(newsItem.getViewCount() + 1);
        newsItemRepository.save(newsItem);
        return newsMapper.toResponse(newsItem);
    }

    // Create new item
    @Transactional
    public NewsResponse createItem(NewsRequest request) {
        NewsItem newsItem = newsMapper.toEntity(request);
        NewsItem savedItem = newsItemRepository.save(newsItem);
        return newsMapper.toResponse(savedItem);
    }

    // Update existing item
    @Transactional
    public NewsResponse updateItem(Long id, NewsRequest request) {
        NewsItem newsItem = newsItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News item not found with id: " + id));

        newsMapper.updateEntity(newsItem, request);
        NewsItem updatedItem = newsItemRepository.save(newsItem);
        return newsMapper.toResponse(updatedItem);
    }

    // Delete item
    @Transactional
    public void deleteItem(Long id) {
        if (!newsItemRepository.existsById(id)) {
            throw new RuntimeException("News item not found with id: " + id);
        }
        newsItemRepository.deleteById(id);
    }

    // Reorder items
    @Transactional
    public void reorderItems(List<Long> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            throw new IllegalArgumentException("Item IDs list cannot be null or empty");
        }

        for (int i = 0; i < itemIds.size(); i++) {
            Long itemId = itemIds.get(i);
            NewsItem item = newsItemRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("News item not found with id: " + itemId));
            item.setDisplayOrder(i);
            newsItemRepository.save(item);
        }
    }

    // Toggle publish status
    @Transactional
    public NewsResponse togglePublishStatus(Long id) {
        NewsItem newsItem = newsItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News item not found with id: " + id));
        newsItem.setIsPublished(!newsItem.getIsPublished());
        NewsItem updatedItem = newsItemRepository.save(newsItem);
        return newsMapper.toResponse(updatedItem);
    }

    // Toggle featured status
    @Transactional
    public NewsResponse toggleFeaturedStatus(Long id) {
        NewsItem newsItem = newsItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News item not found with id: " + id));
        newsItem.setIsFeatured(!newsItem.getIsFeatured());
        NewsItem updatedItem = newsItemRepository.save(newsItem);
        return newsMapper.toResponse(updatedItem);
    }

    // Helper method to map Page to PageResponse
    private PageResponse<NewsResponse> mapToPageResponse(Page<NewsItem> page) {
        List<NewsResponse> content = page.getContent()
                .stream()
                .map(newsMapper::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<NewsResponse>builder()
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