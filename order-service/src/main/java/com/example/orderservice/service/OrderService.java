package com.example.orderservice.service;

import com.example.orderservice.event.OrderCreatedEvent;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        log.info("Order saved: {}", savedOrder.getOrderId());

        // Publish OrderCreatedEvent to Kafka
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getOrderId(),
                savedOrder.getItem(),
                savedOrder.getQuantity(),
                LocalDateTime.now()
        );

        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-topic", savedOrder.getOrderId(), eventJson);
            log.info("Event published to Kafka: {}", event);
        } catch (Exception e) {
            log.error("Error publishing event to Kafka", e);
        }

        return savedOrder;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
