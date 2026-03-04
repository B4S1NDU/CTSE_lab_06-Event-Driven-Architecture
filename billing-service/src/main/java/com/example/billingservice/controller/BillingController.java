package com.example.billingservice.controller;

import com.example.billingservice.model.Invoice;
import com.example.billingservice.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@Slf4j
public class BillingController {
    private final InvoiceRepository invoiceRepository;

    public BillingController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        log.info("Fetching all invoices");
        List<Invoice> invoices = invoiceRepository.findAll();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        log.info("Fetching invoice by ID: {}", id);
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Invoice>> getInvoiceByOrderId(@PathVariable String orderId) {
        log.info("Fetching invoice for order: {}", orderId);
        // Note: This assumes InvoiceRepository can filter by orderId
        // Adjust based on your actual repository implementation
        List<Invoice> invoices = invoiceRepository.findAll().stream()
                .filter(inv -> inv.getOrderId().equals(orderId))
                .toList();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }
}
