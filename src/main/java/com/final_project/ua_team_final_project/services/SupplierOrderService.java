package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.models.Supplier;
import com.final_project.ua_team_final_project.repositories.OrderRepository;
import com.final_project.ua_team_final_project.repositories.OrderedProductRepository;
import com.final_project.ua_team_final_project.repositories.SupplierOrdersRepository;
import com.final_project.ua_team_final_project.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierOrderService {

    private final OrderService orderService;

    private final SupplierRepository supplierRepository;

    public SupplierOrderService(OrderService orderService, SupplierRepository supplierRepository) {
        this.orderService = orderService;
        this.supplierRepository = supplierRepository;
    }

    public Map<String, String> processOrders(List<Long> orderIds) {
        List<OrderedProduct> orderedProducts = orderService.getOrderedProductsForOrders(orderIds);
        // System.out.println(orderedProducts);

        // Orders by supplier
        Map<Long, List<OrderedProduct>> orderedProductsBySupplier = orderedProducts.stream()
                .collect(Collectors.groupingBy(product -> product.getSupplier().getSupplierId()));
//        orderedProductsBySupplier.forEach((supplier, products) -> {
//            System.out.println(supplier + " " + products);
//        });

        Map<String, String> fileLinks = new HashMap<>();

        for (Map.Entry<Long, List<OrderedProduct>> entry : orderedProductsBySupplier.entrySet()) {
            Long supplierId = entry.getKey();
            List<OrderedProduct> products = entry.getValue();

            String fileName = generateFileName(supplierId);
            //String link = generateLink(fileName);

            //byte[] csvData = generateCsvContent(supplierOrders);
            //saveFileToStorage(fileName, csvData);

        }

//        for (Map.Entry<Long, List<Order>> entry : ordersBySupplier.entrySet()) {
//            Long supplierId = entry.getKey();
//            List<Order> supplierOrders = entry.getValue();
//            String fileName = "supplier_" + supplierId + "_" + LocalDate.now() + ".csv";
//
//            // Генерация CSV
//            byte[] csvData = generateCsvContent(supplierOrders);
//
//            // TODO: Записать файл в хранилище
//            saveFileToStorage(fileName, csvData);
//
//            fileLinks.put(fileName, "/storage/" + fileName);
//        }

        return fileLinks;
    }

    private String generateFileName(Long supplierId) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isEmpty()) {
            return null;
        }
        String supplierName = supplier.get().getName();
        return supplierName + "_" + LocalDate.now() + ".csv";
    }

//    private byte[] generateCsvContent(List<Order> orders) {
//        try (StringWriter writer = new StringWriter();
//             CSVWriter csvWriter = new CSVWriter(writer)) {
//
//            csvWriter.writeNext(new String[]{"Order ID", "Department", "Total Price", "Created At"});
//
//            for (Order order : orders) {
//                csvWriter.writeNext(new String[]{
//                        order.getId().toString(),
//                        order.getDept().getName(),
//                        order.getTotalPrice().toString(),
//                        order.getCreatedAt().toString()
//                });
//            }
//
//            return writer.toString().getBytes(StandardCharsets.UTF_8);
//
//        } catch (IOException e) {
//            throw new RuntimeException("Error generating CSV", e);
//        }
//    }
//
//    private void saveFileToStorage(String fileName, byte[] data) {
//        // TODO: Сохранить файл в хранилище (например, S3, FTP, локальную папку)
//        System.out.println("Saving file: " + fileName);
//    }
}

