package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.model.Order;

@Component
public class OrderCon1 {
    @KafkaListener(topics = "orders", groupId = "group1")
    public void consume(Order order) {
        System.out.println("ConsumerA received: " + order);
    }
}