package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import com.final_project.ua_team_final_project.repositories.AvailableProductsRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ProductService {

    private final DigitalOceanStorageService digitalOceanService;
    private final CsvService csvService;
    private final AvailableProductsRepository availableProductsRepository;

    public ProductService(DigitalOceanStorageService digitalOceanService, CsvService csvService, AvailableProductsRepository availableProductsRepository) {
        this.digitalOceanService = digitalOceanService;
        this.csvService = csvService;
        this.availableProductsRepository = availableProductsRepository;
    }

    public void processProducts() {
        List<String> filenames = digitalOceanService.listCsvFiles();
        for (String filename : filenames) {
            try {
                InputStream inputStream = digitalOceanService.downloadFile(filename);
                List<AvailableProducts> products = csvService.parseCsv(inputStream);
                availableProductsRepository.saveAll(products);
                System.out.println("successfully parsed csv file " + filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
