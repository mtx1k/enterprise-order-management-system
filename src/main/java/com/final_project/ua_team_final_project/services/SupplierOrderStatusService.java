package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.SupplierOrder;
import com.final_project.ua_team_final_project.models.SupplierOrderStatus;
import com.final_project.ua_team_final_project.repositories.SupplierOrderStatusRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierOrderStatusService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final SupplierOrderStatusRepository supplierOrderStatusRepository;

    public SupplierOrderStatus findById(Long id) {
        return supplierOrderStatusRepository.findById(id).orElse(null);
    }

    public SupplierOrder changeStatusToSent(Long supplierOrderId) {
        SupplierOrder order = entityManager.find(SupplierOrder.class, supplierOrderId);
        if (order != null) {
            order.setSupplierOrderId(2L);
            entityManager.flush();
        }
        return order;
    }

}
