package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import com.final_project.ua_team_final_project.models.SupplierOrderProduct;
import com.final_project.ua_team_final_project.repositories.SupplierOrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierOrderProductService {

    private final SupplierOrderProductRepository supplierOrderProductRepository;

    public List<SupplierOrderProduct> saveSupplierProducts(List<SupplierOrderProduct> products) {
        return supplierOrderProductRepository.saveAll(products);
    }

    public List<SupplierOrderProduct> processProducts(SupplierOrder supplierOrder, List<OrderedProduct> products) {
        List<SupplierOrderProduct> supplierOrderProducts = new ArrayList<>();
        for (OrderedProduct product : products) {
            SupplierOrderProduct supplierOrderProduct = new SupplierOrderProduct();
            supplierOrderProduct.setSupplierOrder(supplierOrder);
            supplierOrderProduct.setOrderProduct(product);
            supplierOrderProduct.setAmount(product.getAmount());
            supplierOrderProducts.add(supplierOrderProduct);
        }
        return supplierOrderProducts;
    }
}
