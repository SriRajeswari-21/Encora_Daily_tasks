package com.example.demo.controller;

//package com.example.kafka_demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderProducer producer;
    public OrderController(OrderProducer producer) { this.producer = producer; }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        producer.sendOrder(order);
        return ResponseEntity.ok("Order sent: " + order.getOrderId());
    }
}
