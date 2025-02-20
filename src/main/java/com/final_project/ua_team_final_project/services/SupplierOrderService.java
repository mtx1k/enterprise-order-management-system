package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SupplierOrderService {

    private final SupplierRepository supplierRepository;

    private final DigitalOceanStorageService digitalOceanStorageService;

    private final SupplierOrdersRepository supplierOrdersRepository;

    private final SupplierOrderStatusRepository supplierOrderStatusRepository;

    private final SupplierOrderProductService supplierOrderProductService;

    public Map<SupplierOrder, List<SupplierOrderProduct>> processOrders(Map<Long, List<OrderedProduct>> orderedProducts) {

        Map<SupplierOrder, List<SupplierOrderProduct>> orders = new HashMap<>();

        orderedProducts.forEach((supplierId, products) -> {

            Long id = supplierOrdersRepository.count() + 1;

            Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
            if (supplier == null) {
                return;
            }

            SupplierOrderStatus supplierOrderStatus = supplierOrderStatusRepository.findById(1L).orElse(null);

            double price = calculateTotalPrice(products);

            SupplierOrder supplierOrder = new SupplierOrder(id, supplier, price, LocalDateTime.now(), supplierOrderStatus);

            List<SupplierOrderProduct> supplierOrderProducts = supplierOrderProductService.processProducts(supplierOrder, products);

            orders.put(supplierOrder, supplierOrderProducts);

        });

        return orders;
    }

    private double calculateTotalPrice(List<OrderedProduct> products) {
        double totalPrice = 0;
        for (OrderedProduct product : products) {
            totalPrice += product.getItemPrice();
        }
        return totalPrice;
    }

    private String generateFileName(Long supplierId) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isEmpty()) {
            return null;
        }
        String supplierName = supplier.get().getName().replaceAll(" ", "");
        return supplierName + "_" + LocalDate.now() + ".csv";
    }

    public Map<String, OutputStream> generateScvFiles(Map<SupplierOrder, List<SupplierOrderProduct>> orders) {
        Map<String, OutputStream> files = new HashMap<>();

        orders.forEach((order, supplierOrderProducts) -> {
            String fileName = generateFileName(order.getSupplier().getSupplierId());
            Double totalPrice = order.getTotalPrice();

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                 CSVWriter csvWriter = new CSVWriter(writer)) {

                // Header
                csvWriter.writeNext(new String[]{"Product Code", "Name", "Item Price", "Amount"});

                // Products
                for (SupplierOrderProduct supplierOrderProduct : supplierOrderProducts) {
                    OrderedProduct product = supplierOrderProduct.getOrderProduct();
                    csvWriter.writeNext(new String[]{
                            product.getProductCode(),
                            product.getName(),
                            product.getItemPrice().toString(),
                            product.getAmount().toString()
                    });
                }
                csvWriter.writeNext(new String[]{"Price: ", String.format("%.2f", totalPrice)});

                csvWriter.flush();
                files.put(fileName, outputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return files;
    }

    public SupplierOrder saveSupplierOrder(SupplierOrder supplierOrder) {
        return supplierOrdersRepository.save(supplierOrder);
    }
}

