package com.marketing.config;

import com.marketing.entity.*;
import com.marketing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.marketing.entity.Brand;
import com.marketing.repository.BrandRepository;

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
    private PasswordEncoder passwordEncoder;


    @Autowired
    private BrandRepository brandRepository;


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
    }
}
