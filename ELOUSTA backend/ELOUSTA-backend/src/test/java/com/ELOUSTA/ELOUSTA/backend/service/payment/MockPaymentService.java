package com.ELOUSTA.ELOUSTA.backend.service.payment;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MockPaymentService extends PaymentService {

    @Override
    public Map<String, Object> createPaymentIntent(double amount, String currency) {
        Map<String, Object> response = new HashMap<>();
        if (amount <= 0) {
            response.put("error", "Invalid request");
        } else {
            response.put("clientSecret", "mock_client_secret");
        }
        return response;
    }
}
