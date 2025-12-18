package com.marketing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import com.marketing.entity.AboutUs;
import com.marketing.entity.GalleryItem;
import com.marketing.entity.Product;
import com.marketing.repository.AboutUsRepository;
import com.marketing.repository.GalleryItemRepository;
import com.marketing.repository.ProductRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private GalleryItemRepository galleryItemRepository;

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private ProductRepository productRepository;

    public String storeFile(MultipartFile file) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                    : "";
            String filename = UUID.randomUUID().toString() + extension;

            // Copy file to upload directory
            Path targetLocation = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + filename;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
                String filename = fileUrl.substring("/uploads/".length());
                Path filePath = Paths.get(uploadDir).resolve(filename);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file", ex);
        }
    }

    public List<String> findUnusedImages() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                return Collections.emptyList();
            }

            // Get all files in uploads directory
            Set<String> allFiles = Files.list(uploadPath)
                    .filter(Files::isRegularFile)
                    .map(path -> "/uploads/" + path.getFileName().toString())
                    .collect(Collectors.toSet());

            // Get all used image URLs from database
            Set<String> usedImages = new HashSet<>();

            // Add gallery images
            usedImages.addAll(galleryItemRepository.findAll().stream()
                    .map(GalleryItem::getImageUrl)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));

            // Add about us owner image
            aboutUsRepository.findAll().stream()
                    .map(AboutUs::getOwnerImageUrl)
                    .filter(Objects::nonNull)
                    .forEach(usedImages::add);

            // Add product images (imageUrl1 through imageUrl5)
            productRepository.findAll().forEach(product -> {
                if (product.getImageUrl1() != null) usedImages.add(product.getImageUrl1());
                if (product.getImageUrl2() != null) usedImages.add(product.getImageUrl2());
                if (product.getImageUrl3() != null) usedImages.add(product.getImageUrl3());
                if (product.getImageUrl4() != null) usedImages.add(product.getImageUrl4());
                if (product.getImageUrl5() != null) usedImages.add(product.getImageUrl5());
            });

            // Find unused images
            allFiles.removeAll(usedImages);
            return new ArrayList<>(allFiles);

        } catch (IOException ex) {
            throw new RuntimeException("Could not list files", ex);
        }
    }

    public void deleteUnusedImages() {
        List<String> unusedImages = findUnusedImages();
        for (String imageUrl : unusedImages) {
            deleteFile(imageUrl);
        }
    }
}
