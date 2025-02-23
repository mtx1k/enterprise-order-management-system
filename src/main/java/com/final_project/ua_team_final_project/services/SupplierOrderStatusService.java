package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.SupplierOrder;
import com.final_project.ua_team_final_project.models.SupplierOrderStatus;
import com.final_project.ua_team_final_project.repositories.SupplierOrderStatusRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void changeStatusToSent(Long supplierOrderId) {
        SupplierOrder order = entityManager.find(SupplierOrder.class, supplierOrderId);
        if (order != null) {
            SupplierOrderStatus sentStatus = entityManager.createQuery(
                            "SELECT s FROM SupplierOrderStatus s WHERE s.supplierOrderStatusText = 'SENT'",
                            SupplierOrderStatus.class)
                    .getSingleResult();

            order.setSupplierOrderStatus(sentStatus);
            entityManager.flush();
        }
    }

    @Transactional
    public void changeStatusToCancelled(Long supplierOrderId) {
        SupplierOrder order = entityManager.find(SupplierOrder.class, supplierOrderId);
        if (order != null) {
            SupplierOrderStatus cancelledStatus = entityManager.createQuery(
                            "SELECT s FROM SupplierOrderStatus s WHERE s.supplierOrderStatusText = 'CANCELLED'",
                            SupplierOrderStatus.class)
                    .getSingleResult();

            order.setSupplierOrderStatus(cancelledStatus);
            entityManager.flush();
        }
    }
}
