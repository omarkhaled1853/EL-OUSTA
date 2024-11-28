package com.testOTP.TestOtp.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Random;

@Service
public class OtpService {

    private static final String BULK_SMS_URL = "https://api.bulksms.com/v1/messages";
    private static final String USERNAME = "mohamed_mohamed";
    private static final String PASSWORD = "##23122002$$m";
    private final RestTemplate restTemplate = new RestTemplate();
// ##23122002$$m
    public String generateOtp() {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public String sendOtp(String phoneNumber, String otp) {
        String messageBody = "Your OTP is: " + otp;

        // JSON payload
        String payload = String.format("{\"to\": \"%s\", \"encoding\": \"UNICODE\", \"body\": \"%s\"}", phoneNumber, messageBody);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        String authStr = USERNAME + ":" + PASSWORD;
        String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
        headers.set("Authorization", "Basic " + authEncoded);
        headers.set("Content-Type", "application/json");

        // Create request
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        try {
            // Make the request
            ResponseEntity<String> response = restTemplate.postForEntity(BULK_SMS_URL, request, String.class);

            // Handle response
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return "Failed to send OTP. HTTP Response Code: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Failed to send OTP: " + e.getMessage();
        }
    }
}
