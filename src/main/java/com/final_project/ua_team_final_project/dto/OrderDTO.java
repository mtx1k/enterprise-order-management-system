package com.final_project.ua_team_final_project.dto;

import java.time.LocalDateTime;
import java.util.List;


public record OrderDTO(
        Long orderId,
        DepartmentDTO dept,
        double totalPrice,
        boolean approvedByHead,
        boolean approvedByFinDept,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatusDTO status,
        UserDTO user,
        List<OrderedProductDTO> orderedProducts) {
}
