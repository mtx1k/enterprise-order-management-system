package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
public class ParsingService {

    private final CategoryService categoryService;

    public ParsingService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<AvailableProducts> parseCsv(InputStream inputStream, Long supplierId) {
        List<AvailableProducts> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] headers = csvReader.readNext();
            if (headers == null) {
                throw new IllegalArgumentException("Empty CSV file");
            }

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                AvailableProducts product = new AvailableProducts();
                product.setProductCode(line[0]);
                product.setName(line[1]);
                product.setDescription(line[2]);
                if (categoryService.getCategoryIdByName(line[3]).isEmpty()) {
                    categoryService.addCategory(line[3]);
                }
                product.setCategoryId(categoryService.getCategoryIdByName(line[3]).get());
                product.setSupplierId(supplierId);
                product.setPrice(Double.parseDouble(line[4]));

                products.add(product);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return products;
    }

}

