package com.example.billingservice.repository;

import com.example.billingservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Invoice findByOrderId(String orderId);
}
