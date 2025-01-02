package com.ELOUSTA.ELOUSTA.backend.service.otpTest;

import com.ELOUSTA.ELOUSTA.backend.model.TwilioSmsRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwilioSmsRequestTest {
    @Test
    void testTwilioSmsRequest() {
        TwilioSmsRequest smsRequest = new TwilioSmsRequest("1234567890", "Test Message");
        assertEquals("1234567890", smsRequest.getPhonenuber());
        assertEquals("Test Message", smsRequest.getMassage());
        smsRequest.setPhonenuber("0987654321");
        smsRequest.setMassage("Updated Message");
        assertEquals("0987654321", smsRequest.getPhonenuber());
        assertEquals("Updated Message", smsRequest.getMassage());
        String expectedString = "TwilioSmsRequest{phonenuber='0987654321', massage='Updated Message'}";
        assertEquals(expectedString, smsRequest.toString());
    }
}