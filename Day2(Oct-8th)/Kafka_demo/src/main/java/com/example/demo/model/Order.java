package com.example.demo.model;


import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private double amount;
    private List<String> items;

    public Order() {}
    public Order(String orderId, String userId, double amount, List<String> items) {
        this.orderId = orderId; this.userId = userId; this.amount = amount; this.items = items;
    }

    // Getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                ", items=" + items +
                '}';
    }
}
