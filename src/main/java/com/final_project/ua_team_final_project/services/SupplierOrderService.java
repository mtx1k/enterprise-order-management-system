package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.models.Supplier;
import com.final_project.ua_team_final_project.repositories.SupplierRepository;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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

    private final DigitalOceanStorageService digitalOceanStorageService;

    public SupplierOrderService(OrderService orderService, SupplierRepository supplierRepository, DigitalOceanStorageService digitalOceanStorageService) {
        this.orderService = orderService;
        this.supplierRepository = supplierRepository;
        this.digitalOceanStorageService = digitalOceanStorageService;
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

    public void generateAndUploadCsv(Map<Long, List<OrderedProduct>> supplierProducts) {
        LocalDate today = LocalDate.now();

        supplierProducts.forEach((supplierId, products) -> {
            String fileName = "SupplierOrdersHistory/" + "supplier_" + supplierId + "_" + today + ".csv";

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                 CSVWriter csvWriter = new CSVWriter(writer)) {

                // Header
                csvWriter.writeNext(new String[]{"Product Code", "Name", "Item Price", "Amount"});

                // Products
                double price = 0.0;
                for (OrderedProduct product : products) {
                    price += product.getItemPrice() * product.getAmount();
                    csvWriter.writeNext(new String[]{
                            product.getName(),
                            product.getProductCode(),
                            product.getItemPrice().toString(),
                            product.getAmount().toString()
                    });
                }
                csvWriter.writeNext(new String[]{"Price: ", String.format("%.2f", price)});

                csvWriter.flush();
                digitalOceanStorageService.uploadFile(fileName, outputStream.toByteArray());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

