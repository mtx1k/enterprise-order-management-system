package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.models.Supplier;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import com.final_project.ua_team_final_project.repositories.SupplierOrdersRepository;
import com.final_project.ua_team_final_project.repositories.SupplierRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SupplierOrderService {

    private final OrderService orderService;

    private final SupplierRepository supplierRepository;

    private final DigitalOceanStorageService digitalOceanStorageService;
    private final SupplierOrdersRepository supplierOrdersRepository;

    public Map<Long, List<OrderedProduct>> processOrders(List<Long> orderIds) {
        List<OrderedProduct> orderedProducts = orderService.getOrderedProductsForOrders(orderIds);
        // System.out.println(orderedProducts);

        Map<Long, List<OrderedProduct>> orderedProductsBySupplier = orderedProducts.stream()
                .collect(Collectors.groupingBy(product -> product.getSupplier().getSupplierId()));
//        orderedProductsBySupplier.forEach((supplier, products) -> {
//            System.out.println(supplier + " " + products);
//        });
        return orderedProductsBySupplier;
    }

    private String generateFileName(Long supplierId) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isEmpty()) {
            return null;
        }
        String supplierName = supplier.get().getName().replaceAll(" ", "");
        return "SupplierOrdersHistory/" + supplierName + "_" + LocalDate.now() + ".csv";
    }

    public List<String> generateAndUploadCsv(Map<Long, List<OrderedProduct>> supplierProducts) {
        List<String> files = new ArrayList<>();

        supplierProducts.forEach((supplierId, products) -> {
            String fileName = generateFileName(supplierId);

            double price = 0.0;

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                 CSVWriter csvWriter = new CSVWriter(writer)) {

                // Header
                csvWriter.writeNext(new String[]{"Product Code", "Name", "Item Price", "Amount"});

                // Products
                for (OrderedProduct product : products) {
                    price += product.getItemPrice() * product.getAmount();
                    csvWriter.writeNext(new String[]{
                            product.getProductCode(),
                            product.getName(),
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

            saveSupplierOrder(supplierId, price);
            files.add(fileName);

        });
        return files;
    }

    public void saveSupplierOrder(Long supplierId, double price) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isEmpty()) {
            return;
        }
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setSupplier(supplier.get());
        supplierOrder.setTotalPrice(price);
        supplierOrdersRepository.save(supplierOrder);
    }
}

