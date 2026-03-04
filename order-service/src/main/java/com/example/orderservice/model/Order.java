package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String orderId;
    private String item;
    private Integer quantity;
    private String status;
    
    public Order(String orderId, String item, Integer quantity) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
        this.status = "CREATED";
    }
}
