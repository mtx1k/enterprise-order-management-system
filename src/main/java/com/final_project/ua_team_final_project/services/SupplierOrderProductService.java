package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.repositories.SupplierOrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierOrderProductService {

    private final SupplierOrderProductRepository supplierOrderProductRepository;

    public void saveSupplierProducts(Map<Long, List<OrderedProduct>> products) {
        
    }
}
