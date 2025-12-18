package com.marketing.service;

import com.marketing.dto.*;
import com.marketing.entity.GalleryItem;
import com.marketing.mapper.GalleryItemMapper;
import com.marketing.repository.GalleryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GalleryService {

    @Autowired
    private GalleryItemRepository galleryItemRepository;

    @Autowired
    private GalleryItemMapper galleryItemMapper;

    public List<GalleryItemResponse> getAllItems() {
        List<GalleryItem> items = galleryItemRepository.findAllByOrderByDisplayOrderAsc();
        return galleryItemMapper.toResponseList(items);
    }

    public GalleryItemResponse getItemById(Long id) {
        GalleryItem item = galleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery item not found"));
        return galleryItemMapper.toResponse(item);
    }

    public GalleryItemResponse createItem(CreateGalleryItemRequest request) {
        GalleryItem item = galleryItemMapper.toEntity(request);
        GalleryItem savedItem = galleryItemRepository.save(item);
        return galleryItemMapper.toResponse(savedItem);
    }

    public GalleryItemResponse updateItem(Long id, UpdateGalleryItemRequest request) {
        GalleryItem item = galleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery item not found"));
        galleryItemMapper.updateEntity(item, request);
        GalleryItem updatedItem = galleryItemRepository.save(item);
        return galleryItemMapper.toResponse(updatedItem);
    }

    public void deleteItem(Long id) {
        galleryItemRepository.deleteById(id);
    }

    @Transactional
    public void reorderItems(ReorderGalleryRequest request) {
        List<Long> itemIds = request.getGalleryItemIds();
        for (int i = 0; i < itemIds.size(); i++) {
            Long itemId = itemIds.get(i);
            GalleryItem item = galleryItemRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Gallery item not found"));
            item.setDisplayOrder(i);
            galleryItemRepository.save(item);
        }
    }
}
