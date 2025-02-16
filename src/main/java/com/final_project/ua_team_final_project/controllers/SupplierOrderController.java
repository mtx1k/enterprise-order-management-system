package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.services.SupplierOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class SupplierOrderController {

    private final SupplierOrderService supplierOrderService;

    @PostMapping("/generateCsv")
    public ResponseEntity<Map<String, String>> generateCsv(@RequestParam List<Long> selectedOrders) {
        Map<String, String> fileLinks = supplierOrderService.processOrders(selectedOrders);
        return ResponseEntity.ok(fileLinks);
    }
}

