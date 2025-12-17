package com.marketing.config;

import com.marketing.entity.*;
import com.marketing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.marketing.entity.Brand;
import com.marketing.entity.ProductCategory;
import com.marketing.repository.BrandRepository;
import com.marketing.repository.ProductCategoryRepository;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private GalleryItemRepository galleryItemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public void run(String... args) throws Exception {

        // Initialize About Us if not exists
        if (aboutUsRepository.count() == 0) {
            AboutUs aboutUs = new AboutUs();
            aboutUs.setCompanyName("Your Company Name");
            aboutUs.setCompanyDescription("Welcome to our company. We provide excellent services and products.");
            aboutUs.setOwnerName("John Doe");
            aboutUs.setOwnerTitle("CEO & Founder");
            aboutUs.setOwnerDescription("John has over 20 years of experience in the industry.");
            aboutUsRepository.save(aboutUs);
            System.out.println("Default About Us data created");
        }

        // Initialize Contact Info if not exists
        if (contactInfoRepository.count() == 0) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setEmail("contact@example.com");
            contactInfo.setPhone("+1 234 567 8900");
            contactInfo.setAddress("123 Business Street, City, Country");
            contactInfo.setFacebookUrl("https://facebook.com/yourcompany");
            contactInfo.setTwitterUrl("https://twitter.com/yourcompany");
            contactInfo.setInstagramUrl("https://instagram.com/yourcompany");
            contactInfo.setLinkedinUrl("https://linkedin.com/company/yourcompany");
            contactInfoRepository.save(contactInfo);
            System.out.println("Default Contact Info created");
        }

        // Initialize sample gallery items if not exists
        if (galleryItemRepository.count() == 0) {
            GalleryItem item1 = new GalleryItem();
            item1.setTitle("Sample Image 1");
            item1.setDescription("This is a sample gallery item. Replace with your own content.");
            item1.setCategory("Category A");
            item1.setImageUrl("/uploads/sample1.jpg");
            item1.setDisplayOrder(0);
            galleryItemRepository.save(item1);

            GalleryItem item2 = new GalleryItem();
            item2.setTitle("Sample Image 2");
            item2.setDescription("Another sample gallery item for demonstration.");
            item2.setCategory("Category B");
            item2.setImageUrl("/uploads/sample2.jpg");
            item2.setDisplayOrder(1);
            galleryItemRepository.save(item2);

            System.out.println("Sample gallery items created");
        }

        // Initialize sample products if not exists
        if (productRepository.count() == 0) {
            Product product1 = new Product();
            product1.setName("Premium Laptop");
            product1.setDescription("High-performance laptop for professionals");
            product1.setDescription2("Features include latest processor, ample RAM, and SSD storage");
            product1.setCapacity("16GB RAM, 512GB SSD");
            product1.setPrice(new BigDecimal("1299.99"));
            product1.setBrand("TechBrand");
            product1.setCategory("Electronics");
            product1.setWarranty("2 Years");
            product1.setResponsiblePerson("John Doe");
            product1.setImageUrl1("/uploads/sample1.jpg");
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("Wireless Headphones");
            product2.setDescription("Noise-cancelling wireless headphones");
            product2.setDescription2("Long battery life and superior sound quality");
            product2.setCapacity("30 hours playback");
            product2.setPrice(new BigDecimal("299.99"));
            product2.setBrand("AudioPro");
            product2.setCategory("Electronics");
            product2.setWarranty("1 Year");
            product2.setResponsiblePerson("Jane Smith");
            product2.setImageUrl1("/uploads/sample2.jpg");
            productRepository.save(product2);

            System.out.println("Sample products created");
        }

        // Initialize default admin
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setLicense(true); // Changed from setRole
            adminRepository.save(admin);
            System.out.println("Default admin created: username=admin, password=admin123");
        }

        // Initialize brands if not exists
        if (brandRepository.count() == 0) {
            Brand brand1 = new Brand();
            brand1.setName("TechBrand");
            brandRepository.save(brand1);

            Brand brand2 = new Brand();
            brand2.setName("AudioPro");
            brandRepository.save(brand2);

            Brand brand3 = new Brand();
            brand3.setName("SmartDevices");
            brandRepository.save(brand3);

            System.out.println("Sample brands created");
        }

// Initialize categories if not exists
        if (productCategoryRepository.count() == 0) {
            ProductCategory category1 = new ProductCategory();
            category1.setName("Electronics");
            productCategoryRepository.save(category1);

            ProductCategory category2 = new ProductCategory();
            category2.setName("Computers");
            productCategoryRepository.save(category2);

            ProductCategory category3 = new ProductCategory();
            category3.setName("Audio Equipment");
            productCategoryRepository.save(category3);

            System.out.println("Sample categories created");
        }
    }
}
