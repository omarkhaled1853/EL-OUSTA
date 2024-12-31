package com.ELOUSTA.ELOUSTA.backend.service.payment;

import com.ELOUSTA.ELOUSTA.backend.service.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
        ReflectionTestUtils.setField(paymentService, "secretKey", "sk_test_51QW6k4HqAbjkHma4MieFsLHLmqLaSf45oQCOZLV43VOpZE5ln1pIW1bRoY24jrMkMt8fScNVolifKt2d2uAbM9al00FiNNI5BJ");
    }

    @Test
    void testCreatePaymentIntentSuccess() {
        // Arrange
        int amount = 50;
        String currency = "USD";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert
        assertNotNull(response.get("clientSecret"));
    }

    @Test
    void testCreatePaymentIntentFailure() {
        // Arrange
        int amount = 0; // Invalid amount
        String currency = "USD";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert
        assertNotNull(response.get("error"));
        String errorMessage = (String) response.get("error");

        // Check that the message contains the expected part
        assertTrue(errorMessage.contains("The amount must be greater than or equal to the minimum charge amount"),
                "The error message does not contain the expected prefix.");
    }

    @Test
    void testCreatePaymentIntentBigamount() {
        // Arrange
        int amount = 10000000; // Invalid amount
        String currency = "USD";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert
        assertNotNull(response.get("error"));
        String errorMessage = (String) response.get("error");

        // Check that the message contains the expected part
        assertTrue(errorMessage.contains("Amount must be no more than $999,999.99"),
                "The error message does not contain the expected prefix.");
    }

    @Test
    void testCreatePaymentInvalidCurrency() {
        // Arrange
        int amount = 100; // Invalid amount
        String currency = "eg";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert
        assertNotNull(response.get("error"));
        String errorMessage = (String) response.get("error");

        // Check that the message contains the expected part
        assertTrue(errorMessage.contains("Invalid currency: eg"),
                "The error message does not contain the expected prefix.");
    }
    @Test
    void testCreatePaymentDoubleamount() {
        // Arrange
        double amount = 50.5;
        String currency = "USD";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert
        assertNotNull(response.get("clientSecret"));
    }
    @Test
    void testCreatePaymentNegativeAmount() {
        // Arrange
        double amount = -50;
        String currency = "USD";

        // Act
        Map<String, Object> response = paymentService.createPaymentIntent(amount, currency);

        // Assert

        assertNotNull(response.get("error"));
        String errorMessage = (String) response.get("error");

        // Check that the message contains the expected part
        assertTrue(errorMessage.contains("This value must be greater than or equal to 1"),
                "The error message does not contain the expected prefix.");
    }

}
