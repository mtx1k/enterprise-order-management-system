package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import com.final_project.ua_team_final_project.models.SupplierOrderProduct;
import com.final_project.ua_team_final_project.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

//0 - create ordered products list +
//1 - create supp orders +
//1.1 - create supp products +
//2 - save products +
//3 - update order status
//3.1 - update supplier order status
//4 - create CSVs +
//5 - save to DO history +
//6 - send by email
//7 - update status

@RestController
@RequiredArgsConstructor
public class SupplierOrderController {

    private final OrderService orderService;
    private final SupplierOrderService supplierOrderService;
    private final SupplierOrderProductService supplierOrderProductService;
    private final DigitalOceanStorageService digitalOceanStorageService;
    private final SupplierOrderStatusService supplierOrderStatusService;

//    @PostMapping("/supplier-orders")
//    public ResponseEntity<List<String>> createSupplierOrders(@RequestParam List<Long> selectedOrders) {
//
//        Map<Long, List<OrderedProduct>> orderedProducts = orderService.getOrderedProductsForOrdersBySupplier(selectedOrders);
//
//        Map<SupplierOrder, List<SupplierOrderProduct>> supplierOrders = supplierOrderService.processOrders(orderedProducts);
//
//        supplierOrders.forEach((supplierOrder, orderProducts) -> {
//            SupplierOrder resultSupplierOrder = supplierOrderService.saveSupplierOrder(supplierOrder);
//            if (resultSupplierOrder != null) {
//                List<SupplierOrderProduct> resultSupplierOrderProducts = supplierOrderProductService.saveSupplierProducts(orderProducts);
//                if (resultSupplierOrderProducts == null) {
//                    System.out.println("Supplier order product save failed");
//                }
//
//            } else {
//                System.out.println("Supplier order id " + supplierOrder.getSupplierOrderId() + " not saved");
//            }
//        });
//
//        //List<String> files = supplierOrderService.generateAndUploadCsv(orderedProducts);
//
//        return ResponseEntity.ok(null);
//    }

    @PostMapping("/generateCsv")
    public ResponseEntity<List<String>> generateCsv(@RequestParam List<Long> selectedOrders) {

        Map<Long, List<OrderedProduct>> orderedProducts = orderService.getOrderedProductsForOrdersBySupplier(selectedOrders);

        Map<SupplierOrder, List<SupplierOrderProduct>> supplierOrders = supplierOrderService.processOrders(orderedProducts);

        supplierOrders.forEach((supplierOrder, orderProducts) -> {
            SupplierOrder resultSupplierOrder = supplierOrderService.saveSupplierOrder(supplierOrder);
            if (resultSupplierOrder != null) {
                List<SupplierOrderProduct> resultSupplierOrderProducts = supplierOrderProductService.saveSupplierProducts(orderProducts);
                if (resultSupplierOrderProducts == null) {
                    System.out.println("Supplier order product save failed");
                }

            } else {
                System.out.println("Supplier order id " + supplierOrder.getSupplierOrderId() + " not saved");
            }
        });

        //---------------------------------------------------------

        Map<String, OutputStream> files = supplierOrderService.generateScvFiles(supplierOrders);
        List<String> csvFiles = digitalOceanStorageService.uploadFiles(files);


        //changing status to SENT
//        supplierOrders.keySet().forEach(supplierOrder -> {
//            supplierOrderStatusService.changeStatusToSent(supplierOrder.getSupplierOrderId());
//        });

        return ResponseEntity.ok(csvFiles);
    }
}

