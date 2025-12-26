package com.marketing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import com.marketing.entity.AboutUs;
import com.marketing.repository.AboutUsRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

//    @Autowired
//    private GalleryItemRepository galleryItemRepository;

    @Autowired
    private AboutUsRepository aboutUsRepository;

    public String storeFile(MultipartFile file) {
        try {
            logger.info("Starting file upload process");
            logger.info("Upload directory configured as: {}", uploadDir);

            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                logger.info("Upload directory does not exist. Creating: {}", uploadPath.toAbsolutePath());
                Files.createDirectories(uploadPath);
            }

            logger.info("Upload directory absolute path: {}", uploadPath.toAbsolutePath());

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String filename = UUID.randomUUID().toString() + extension;

            // Copy file to upload directory
            Path targetLocation = uploadPath.resolve(filename);
            logger.info("Saving file to: {}", targetLocation.toAbsolutePath());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/uploads/" + filename;
            logger.info("File uploaded successfully. URL: {}", fileUrl);
            return fileUrl;
        } catch (IOException ex) {
            logger.error("Failed to store file", ex);
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
                String filename = fileUrl.substring("/uploads/".length());
                Path filePath = Paths.get(uploadDir).resolve(filename);
                logger.info("Attempting to delete file: {}", filePath.toAbsolutePath());
                boolean deleted = Files.deleteIfExists(filePath);
                if (deleted) {
                    logger.info("File deleted successfully: {}", fileUrl);
                } else {
                    logger.warn("File not found for deletion: {}", fileUrl);
                }
            }
        } catch (IOException ex) {
            logger.error("Failed to delete file: {}", fileUrl, ex);
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

//            // Add gallery images
//            usedImages.addAll(galleryItemRepository.findAll().stream()
//                    .map(GalleryItem::getImageUrl)
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toSet()));

            // Add about us owner image
            aboutUsRepository.findAll().stream()
                    .map(AboutUs::getOwnerImageUrl)
                    .filter(Objects::nonNull)
                    .forEach(usedImages::add);

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
