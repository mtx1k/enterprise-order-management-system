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

    private final OrderService orderService;

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
        return "SupplierOrdersHistory/" + supplierName + "_" + LocalDate.now() + ".csv";
    }

    public Map<String, OutputStream> generateCSVOutputStream(Map<Long, List<OrderedProduct>> supplierProducts) {

        return null;
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

            files.add(fileName);

        });
        return files;
    }

    public SupplierOrder saveSupplierOrder(SupplierOrder supplierOrder) {
        return supplierOrdersRepository.save(supplierOrder);
    }
}

