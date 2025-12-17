package com.marketing.config;

import com.marketing.entity.AboutUs;
import com.marketing.entity.Admin;
import com.marketing.entity.ContactInfo;
import com.marketing.entity.GalleryItem;
import com.marketing.repository.AboutUsRepository;
import com.marketing.repository.AdminRepository;
import com.marketing.repository.ContactInfoRepository;
import com.marketing.repository.GalleryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            adminRepository.save(admin);
            System.out.println("Default admin created: username=admin, password=admin123");
        }

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
    }
}
