package com.marketing.repository;

import com.marketing.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:search IS NULL OR :search = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:category IS NULL OR :category = '' OR p.category = :category) " +
            "AND (:brand IS NULL OR :brand = '' OR p.brand = :brand)")
    Page<Product> searchProducts(
            @Param("search") String search,
            @Param("category") String category,
            @Param("brand") String brand,
            Pageable pageable
    );
}