package com.example.springsecurity;

import com.example.springsecurity.Modle.OtpBody;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtpBodyTest {
    @Test
    void testOtpBody() {
        // Create an instance of OtpBody using the constructor
        OtpBody otpBody = new OtpBody("1234567890", "987654");

        // Assert initial values
        assertEquals("1234567890", otpBody.getUserphonenumber());
        assertEquals("987654", otpBody.getOtp());

        // Update values using setters
        otpBody.setUserphonenumber("0987654321");
        otpBody.setOtp("123456");

        // Assert updated values
        assertEquals("0987654321", otpBody.getUserphonenumber());
        assertEquals("123456", otpBody.getOtp());
    }

}