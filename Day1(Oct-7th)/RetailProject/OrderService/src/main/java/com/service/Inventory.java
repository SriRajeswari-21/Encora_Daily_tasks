package com.service;


 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
 
@Service
public class Inventory {
 
    private final TestOrder testorder;
    private final List<String> message = new ArrayList<>();
 
    public Inventory(TestOrder testorder){
        this.testorder = testorder;
    }
 
    // Corrected property placeholder
    @KafkaListener(topics = "${topic.inventory}", groupId = "inventory-group")
    public void consumer(String message1) {
        System.out.println("Received order: " + message1);
        message.add(message1);
    }
 
    public List<String> getMessage() {
        return message;
    }
}
 
 