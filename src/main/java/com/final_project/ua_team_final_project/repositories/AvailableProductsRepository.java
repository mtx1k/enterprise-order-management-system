package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import com.final_project.ua_team_final_project.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AvailableProductsRepository extends JpaRepository<AvailableProducts, Long> {

//    @Modifying
//    @Query(value = "ALTER TABLE AvailableProducts AUTO_INCREMENT = 1", nativeQuery = true)
//    void resetAutoIncrement();

    Optional<AvailableProducts> findByProductCode(String productCode);
    Optional<AvailableProducts> findByName(String name);
    Optional<AvailableProducts> findByCategory(Category category);
    Optional<AvailableProducts> findByProductCodeAndCategory(String productCode, Category category);
    Optional<AvailableProducts> findAllByCategory(Category category);
    Optional<AvailableProducts> findAllByNameAndCategory(String productName, Category category);
    Optional<AvailableProducts> findByPrice(Double price);
//    List<AvailableProducts> findByNameContainingIgnoreCase(String name);
    Page<AvailableProducts> findByNameContainingIgnoreCase(String productName, Pageable pageable);
}
