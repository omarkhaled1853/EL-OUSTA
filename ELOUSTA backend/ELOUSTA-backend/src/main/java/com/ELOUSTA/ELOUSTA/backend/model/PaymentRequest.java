package com.ELOUSTA.ELOUSTA.backend.model;

public class PaymentRequest {
    private double amount;
    private String currency;

    // Getters and setters
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}