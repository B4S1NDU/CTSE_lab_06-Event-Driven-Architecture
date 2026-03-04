package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ObjectMapper objectMapper;

    public InventoryService(InventoryRepository inventoryRepository, ObjectMapper objectMapper) {
        this.inventoryRepository = inventoryRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consumeOrderCreatedEvent(String message) {
        log.info("Inventory Service consuming message: {}", message);
        try {
            com.example.inventoryservice.event.OrderCreatedEvent event = 
                objectMapper.readValue(message, com.example.inventoryservice.event.OrderCreatedEvent.class);
            
            log.info("Event received: {}", event);
            
            // Check inventory and update
            Inventory inventory = new Inventory();
            inventory.setOrderId(event.getOrderId());
            inventory.setItem(event.getItem());
            inventory.setQuantity(event.getQuantity());
            inventory.setStatus("STOCK_UPDATED");
            
            inventoryRepository.save(inventory);
            log.info("Inventory updated for order: {}", event.getOrderId());
            
        } catch (Exception e) {
            log.error("Error processing order event", e);
        }
    }
}
