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

    private final OrderStatusRepository orderStatusRepository;

    private final SupplierOrderProductService supplierOrderProductService;

    private final SupplierOrderStatusRepository supplierOrderStatusRepository;

    public Map<Long, List<OrderedProduct>> processOrders(Map<Long, List<OrderedProduct>> orderedProducts) {



        orderedProducts.forEach((supplierId, products) -> {

            Long id = supplierOrdersRepository.count() + 1;

            Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
            if (supplier == null) {
                return;
            }

            SupplierOrderStatus supplierOrderStatus = supplierOrderStatusRepository.findById(1L).orElse(null);

            double price = 0;

            SupplierOrder supplierOrder = new SupplierOrder(id, supplier, price, LocalDateTime.now(), supplierOrderStatus);



        });

        return null;
    }

    private String generateFileName(Long supplierId) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isEmpty()) {
            return null;
        }
        String supplierName = supplier.get().getName().replaceAll(" ", "");
        return "SupplierOrdersHistory/" + supplierName + "_" + LocalDate.now() + ".csv";
    }

    public Map<String, OutputStream> generateCSVsOutputStream(Map<Long, List<OrderedProduct>> supplierProducts) {

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

            SupplierOrder supplierOrder = saveSupplierOrder(supplierId, price);
            supplierOrderProductService.saveSupplierProductsByOrder(supplierOrder, products);
            files.add(fileName);

        });
        return files;
    }

    public SupplierOrder saveSupplierOrder(Long supplierId, double price) {
        Optional<Supplier> supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier.isEmpty()) {
            return null;
        }
        Optional<OrderStatus> orderStatus = orderStatusRepository.findById(1L);
        if (orderStatus.isEmpty()) {
            return null;
        }
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setSupplier(supplier.get());
        supplierOrder.setTotalPrice(price);
        supplierOrder.setOrderStatus(orderStatus.get());

        return supplierOrdersRepository.save(supplierOrder);
    }
}

