package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import com.final_project.ua_team_final_project.models.Category;
import com.final_project.ua_team_final_project.repositories.CategoryRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CsvService {

    private final CategoryRepository categoryRepository;

    public CsvService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
                product.setCategoryId(getCategoryId(line[3]));
                product.setSupplierId(supplierId);
                product.setPrice(Double.parseDouble(line[4]));

                products.add(product);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return products;
    }

    private Long getCategoryId(String categoryName) {
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if (category.isEmpty()) {
            Long categoryId = categoryRepository.count() + 1;
            Category newCategory = new Category();
            newCategory.setCategoryId(categoryId);
            newCategory.setName(categoryName);
            categoryRepository.save(newCategory);
            return categoryId;
        }
        return category.get().getCategoryId();
    }

}

