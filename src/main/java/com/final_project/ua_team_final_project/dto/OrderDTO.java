package com.final_project.ua_team_final_project.dto;

import com.final_project.ua_team_final_project.models.Department;
import com.final_project.ua_team_final_project.models.OrderedProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


public record OrderDTO(
        Long orderId,
        Long deptId,
        double totalPrice,
        boolean approvedByHead,
        boolean approvedByFinDept,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long statusId,
        List<OrderedProductDTO> orderedProducts) {


}
