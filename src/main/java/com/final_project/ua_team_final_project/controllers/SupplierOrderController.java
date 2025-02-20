package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import com.final_project.ua_team_final_project.services.OrderService;
import com.final_project.ua_team_final_project.services.SupplierOrderProductService;
import com.final_project.ua_team_final_project.services.SupplierOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//0 - create ordered products list
//1 - create supp orders
//1.1 - create supp products
//2 - save products
//3 - update order status
//3.1 - update supplier order status
//4 - create CSVs
//5 - save to DO history
//6 - send by email

@RestController
@RequiredArgsConstructor
public class SupplierOrderController {

    private final OrderService orderService;
    private final SupplierOrderService supplierOrderService;
    private final SupplierOrderProductService supplierOrderProductService;

    @PostMapping("/supplier-orders")
    public ResponseEntity<List<String>> createSupplierOrders(@RequestParam List<Long> selectedOrders) {

        Map<Long, List<OrderedProduct>> orderedProducts = orderService.getOrderedProductsForOrdersBySupplier(selectedOrders);

        Map<Long, List<OrderedProduct>> supplierOrders = supplierOrderService.processOrders(orderedProducts);

        supplierOrderProductService.saveSupplierOrderProducts(supplierOrders);

        //supplierOrderProductService.saveSupplierOrderProducts(orderedProducts);
        //List<String> files = supplierOrderService.generateAndUploadCsv(orderedProducts);

        //TODO add status change for order
        return ResponseEntity.ok(null);
    }

    @PostMapping("/generateCsv")
    public ResponseEntity<List<String>> generateCsv(@RequestParam List<Long> selectedOrders) {


        //Map<Long, List<OrderedProduct>> orderedProducts = supplierOrderService.processOrders(selectedOrders);
        //List<String> files = supplierOrderService.generateAndUploadCsv(orderedProducts);

        //TODO add status change for order
        return ResponseEntity.ok(null);
    }
}

