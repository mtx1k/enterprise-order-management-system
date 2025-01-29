package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailableProductsRepository extends JpaRepository<AvailableProducts, Long> {

    Optional<AvailableProducts> findByProductCode(String productCode);
    Optional<AvailableProducts> findByName(String name);
    Optional<AvailableProducts> findByCategoryId(Long categoryId);
    Optional<AvailableProducts> findByProductCodeAndCategoryId(String productCode, Long categoryId);
    Optional<AvailableProducts> findAllByCategoryId(Long categoryId);
    Optional<AvailableProducts> findAllByNameAndCategoryId(String productName, Long categoryId);

}
