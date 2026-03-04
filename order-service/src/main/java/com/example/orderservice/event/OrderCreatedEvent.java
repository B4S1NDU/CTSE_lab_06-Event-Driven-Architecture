package com.example.orderservice.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String item;
    private Integer quantity;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    private String eventType = "OrderCreated";
    
    // Constructor with 4 parameters (eventType defaults to "OrderCreated")
    public OrderCreatedEvent(String orderId, String item, Integer quantity, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.eventType = "OrderCreated";
    }
    
    // Full constructor with all parameters
    public OrderCreatedEvent(String orderId, String item, Integer quantity, LocalDateTime timestamp, String eventType) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }
}
