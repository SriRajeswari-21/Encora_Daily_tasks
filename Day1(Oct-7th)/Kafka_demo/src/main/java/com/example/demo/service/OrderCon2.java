package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.model.Order;

@Component
public class OrderCon2 {
	
	 @KafkaListener(topics = "orders", groupId = "group1") // same group
	    public void consume(Order order) {
	        System.out.println("ConsumerB received: " + order);
	    }

}
