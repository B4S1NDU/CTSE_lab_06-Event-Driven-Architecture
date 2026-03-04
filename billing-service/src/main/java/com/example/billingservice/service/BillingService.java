package com.example.billingservice.service;

import com.example.billingservice.model.Invoice;
import com.example.billingservice.repository.InvoiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillingService {
    private final InvoiceRepository invoiceRepository;
    private final ObjectMapper objectMapper;

    public BillingService(InvoiceRepository invoiceRepository, ObjectMapper objectMapper) {
        this.invoiceRepository = invoiceRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-topic", groupId = "billing-group")
    public void consumeOrderCreatedEvent(String message) {
        log.info("Billing Service consuming message: {}", message);
        try {
            com.example.billingservice.event.OrderCreatedEvent event = 
                objectMapper.readValue(message, com.example.billingservice.event.OrderCreatedEvent.class);
            
            log.info("Event received: {}", event);
            
            // Generate invoice
            Invoice invoice = new Invoice();
            invoice.setOrderId(event.getOrderId());
            invoice.setItem(event.getItem());
            invoice.setQuantity(event.getQuantity());
            invoice.setAmount(event.getQuantity() * 100.0); // Assume 100 per item
            invoice.setStatus("INVOICE_GENERATED");
            
            invoiceRepository.save(invoice);
            log.info("Invoice generated for order: {}", event.getOrderId());
            
        } catch (Exception e) {
            log.error("Error processing order event", e);
        }
    }
}
