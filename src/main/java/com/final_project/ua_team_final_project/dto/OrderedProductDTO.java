package com.final_project.ua_team_final_project.dto;

public record OrderedProductDTO(
        Long orderedProductId,
        Long orderId,
        String name,
        String productCode,
        Double itemPrice,
        Long categoryId,
        Long supplierId,
        Long amount
) {
}
