package com.example.demo.service;

//package com.example.kafka_demo.service;

import com.example.demo.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @KafkaListener(topics = "orders", groupId = "order-service-group")
    public void consume(Order order, @Header("kafka_receivedPartitionId") int partition) {
        System.out.println("Consumer C Received order from partition " + partition + ": " + order);
    }
}
