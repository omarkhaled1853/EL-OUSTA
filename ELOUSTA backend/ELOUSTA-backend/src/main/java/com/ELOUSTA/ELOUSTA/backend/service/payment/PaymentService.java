package com.ELOUSTA.ELOUSTA.backend.service.payment;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    @Value("${stripe.secret.key}")
    private String secretKey;

    public Map<String, Object> createPaymentIntent(double amount, String currency) {
        Stripe.apiKey = secretKey;

        Map<String, Object> response = new HashMap<>();
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount * 100)) // Convert to cents
                    .setCurrency(currency)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            response.put("clientSecret", paymentIntent.getClientSecret());
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }
}
