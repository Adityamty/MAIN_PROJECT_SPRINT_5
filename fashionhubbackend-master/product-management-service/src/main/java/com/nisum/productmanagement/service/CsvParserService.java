package com.nisum.productmanagement.service;

import com.nisum.productmanagement.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvParserService {

    public List<Product> parseCsv(MultipartFile file) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length >= 5) {
                    Product product = new Product();
                    product.setName(fields[0].trim());
                    product.setCategoryName(fields[2].trim());
                    product.setStatus(fields[3].trim());

                    // Parse lastModifiedDate
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        product.setLastModifiedDate(LocalDateTime.parse(fields[4].trim(), formatter));
                    } catch (Exception e) {
                        product.setLastModifiedDate(LocalDateTime.now());
                    }

                    // Parse sellerId
                    try {
                        product.setSellerId(Long.parseLong(fields[5].trim()));
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Invalid sellerId in CSV: " + fields[5]);
                    }

                    products.add(product);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage(), e);
        }

        return products;
    }
}
