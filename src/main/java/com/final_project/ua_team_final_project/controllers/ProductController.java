package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> importProducts() {
        productService.processProducts();
        return ResponseEntity.ok("Import started successfully");
    }
}
