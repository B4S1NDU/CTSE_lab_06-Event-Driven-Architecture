package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        log.info("Fetching all inventory records");
        List<Inventory> inventory = inventoryRepository.findAll();
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        log.info("Fetching inventory by ID: {}", id);
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if (inventory != null) {
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Inventory>> getInventoryByOrderId(@PathVariable String orderId) {
        log.info("Fetching inventory for order: {}", orderId);
        // Note: This assumes InventoryRepository can filter by orderId
        // Adjust based on your actual repository implementation
        List<Inventory> inventory = inventoryRepository.findAll().stream()
                .filter(inv -> inv.getOrderId().equals(orderId))
                .toList();
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
}
