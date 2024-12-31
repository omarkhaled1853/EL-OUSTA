package com.ELOUSTA.ELOUSTA.backend.model;

import lombok.Getter;

@Getter
public class PaymentRequest {
    private double amount;
    private String currency;

    public void setAmount(double amount) { this.amount = amount; }

    public void setCurrency(String currency) { this.currency = currency; }
}