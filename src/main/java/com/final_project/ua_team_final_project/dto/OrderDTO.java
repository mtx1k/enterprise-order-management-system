package com.final_project.ua_team_final_project.dto;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderDTO {
    private Long orderId;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderedProduct> orderedProducts;

}
