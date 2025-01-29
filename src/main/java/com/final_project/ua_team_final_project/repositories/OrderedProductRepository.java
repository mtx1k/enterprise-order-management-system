package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    Optional<OrderedProduct> findByOrderedProductId(Long orderedProductId);
    Optional<OrderedProduct> findByOrdersOrderId(Long ordersOrderId);
    Optional<OrderedProduct> findByName(String name);
    Optional<OrderedProduct> findByProductCode(String productCode);
    Optional<OrderedProduct> findAllByItemPrice(double itemPrice);
    Optional<OrderedProduct> findAllByCategoryId(Long categoryId);
    Optional<OrderedProduct> findAllBySupplierId(Long supplierId);
    Optional<OrderedProduct> findAllByAmount(Long amount);


}
