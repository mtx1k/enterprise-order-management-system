package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import com.final_project.ua_team_final_project.models.Supplier;
import com.final_project.ua_team_final_project.repositories.AvailableProductsRepository;
import com.final_project.ua_team_final_project.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final DigitalOceanStorageService digitalOceanService;
    private final ParsingService csvService;
    private final AvailableProductsRepository availableProductsRepository;
    private final SupplierRepository supplierRepository;
    private final TruncateService truncateService;

    public ProductService(DigitalOceanStorageService digitalOceanService, ParsingService csvService, AvailableProductsRepository availableProductsRepository, SupplierRepository supplierRepository, TruncateService truncateService) {
        this.digitalOceanService = digitalOceanService;
        this.csvService = csvService;
        this.availableProductsRepository = availableProductsRepository;
        this.supplierRepository = supplierRepository;
        this.truncateService = truncateService;
    }

    public void processProducts() {
        List<String> filenames = digitalOceanService.listCsvFiles();

        if (availableProductsRepository.count() > 0) {
            truncateService.truncateAvailableProductsTable();
        }

        for (String filename : filenames) {

            Long supplierId = getSupplierId(filename);

            if (supplierId == null) {
                continue;
            }

            try {
                InputStream inputStream = digitalOceanService.downloadFile(filename);
                List<AvailableProducts> products = csvService.parseCsv(inputStream, supplierId);
                availableProductsRepository.saveAll(products);
                System.out.println("successfully parsed csv file " + filename);
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }
    }

    private Long getSupplierId(String fileName) {

        String supplierName = extractSupplierName(fileName);

        Optional<Supplier> supplier = supplierRepository.findByName(supplierName);

        if (supplier.isEmpty()) {
            System.out.println("Supplier with name '" + supplierName + "' doesn't exist");
            return null;
        }

        return supplier.get().getSupplierId();
    }

    private String extractSupplierName(String fileName) {
        String[] parts = fileName.split("-");

        return String.join(" ", Arrays.copyOfRange(parts, 0, parts.length - 1));
    }

}
