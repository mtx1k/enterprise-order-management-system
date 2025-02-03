package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvService {

    public List<AvailableProducts> parseCsv(InputStream inputStream) {
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            CsvToBean<AvailableProducts> csvToBean = new CsvToBeanBuilder<AvailableProducts>(reader)
                    .withType(AvailableProducts.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(',')
                    .withIgnoreEmptyLine(true)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
