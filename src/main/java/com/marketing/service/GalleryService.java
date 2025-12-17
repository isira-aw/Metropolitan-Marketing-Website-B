package com.marketing.service;

import com.marketing.entity.GalleryItem;
import com.marketing.repository.GalleryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GalleryService {

    @Autowired
    private GalleryItemRepository galleryItemRepository;

    public List<GalleryItem> getAllItems() {
        return galleryItemRepository.findAllByOrderByDisplayOrderAsc();
    }

    public GalleryItem getItemById(Long id) {
        return galleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery item not found"));
    }

    public GalleryItem createItem(GalleryItem item) {
        if (item.getDisplayOrder() == null) {
            item.setDisplayOrder(0);
        }
        return galleryItemRepository.save(item);
    }

    public GalleryItem updateItem(Long id, GalleryItem updatedItem) {
        GalleryItem item = getItemById(id);
        item.setTitle(updatedItem.getTitle());
        item.setDescription(updatedItem.getDescription());
        item.setCategory(updatedItem.getCategory());
        if (updatedItem.getImageUrl() != null) {
            item.setImageUrl(updatedItem.getImageUrl());
        }
        if (updatedItem.getDisplayOrder() != null) {
            item.setDisplayOrder(updatedItem.getDisplayOrder());
        }
        return galleryItemRepository.save(item);
    }

    public void deleteItem(Long id) {
        galleryItemRepository.deleteById(id);
    }

    @Transactional
    public void reorderItems(List<Long> itemIds) {
        for (int i = 0; i < itemIds.size(); i++) {
            Long itemId = itemIds.get(i);
            GalleryItem item = getItemById(itemId);
            item.setDisplayOrder(i);
            galleryItemRepository.save(item);
        }
    }
}
