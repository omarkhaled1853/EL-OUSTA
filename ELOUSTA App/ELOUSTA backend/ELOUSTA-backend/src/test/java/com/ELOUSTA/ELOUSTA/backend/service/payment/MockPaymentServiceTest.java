// Tests for the PaymentService
package com.ELOUSTA.ELOUSTA.backend.service.payment;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class MockPaymentServiceTest {

    private MockPaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new MockPaymentService(); // Use the mock service
    }

    @Test
    void testCreatePaymentIntentSuccess() {
        // Arrange
        double amount = 50.0;
        String currency = "usd";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert
        assertEquals("mock_client_secret", response.get("clientSecret"));
    }

    @Test
    void testCreatePaymentIntentFailure() {
        // Arrange
        double amount = 0.0; // Invalid amount to simulate failure
        String currency = "usd";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert
        assertEquals("Invalid request", response.get("error"));
    }
}
