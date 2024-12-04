package com.example.springsecurity.service;

import com.example.springsecurity.Modle.OtpBody;
import com.example.springsecurity.Modle.TwilioSmsRequest;
import com.example.springsecurity.TwilioConfiguration;
import com.example.springsecurity.service.TwilioSendSms;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

class TwilioSendSmsTest {

    private TwilioSendSms twilioSendSms;
    private TwilioConfiguration mockTwilioConfiguration;

    @BeforeEach
    void setUp() {
        mockTwilioConfiguration = mock(TwilioConfiguration.class);
        when(mockTwilioConfiguration.getTrial_number()).thenReturn("+1234567890");
        twilioSendSms = new TwilioSendSms(mockTwilioConfiguration);
    }

    @Test
    void testGenerateOtp() {
        String otp = twilioSendSms.generateOtp();
        assertNotNull(otp, "OTP should not be null");
        assertTrue(otp.matches("\\d{6}"), "OTP should be a 6-digit number");
    }

    @Test
    void testSendTwilioSms() {
        try (MockedStatic<Message> mockedMessage = Mockito.mockStatic(Message.class)) {
            TwilioSmsRequest request = new TwilioSmsRequest("+9876543210", "Test Message");

            MessageCreator mockCreator = mock(MessageCreator.class);
            mockedMessage.when(() -> Message.creator(
                            any(PhoneNumber.class), any(PhoneNumber.class), anyString()))
                    .thenReturn(mockCreator);

            twilioSendSms.sendtwiliosms(request);

            mockedMessage.verify(() -> Message.creator(
                    eq(new PhoneNumber("+9876543210")),
                    eq(new PhoneNumber("+1234567890")),
                    startsWith("the verification number is : ")
            ));

            assertTrue(twilioSendSms.otpmap.containsKey("+9876543210"),
                    "OTP map should contain the phone number");
            assertNotNull(twilioSendSms.otpmap.get("+9876543210"),
                    "OTP map should contain a generated OTP");
        }
    }


    @Test
    void testVerification() {
        String phoneNumber = "+9876543210";
        String generatedOtp = "123456";
        twilioSendSms.otpmap = new HashMap<>();
        twilioSendSms.otpmap.put(phoneNumber, generatedOtp);

        OtpBody otpBody = new OtpBody(phoneNumber, generatedOtp);

        boolean isVerified = twilioSendSms.verification(otpBody);

        assertTrue(isVerified, "Verification should return true for a valid OTP");
    }

    @Test
    void testVerification_Failure() {
        String phoneNumber = "+9876543210";
        twilioSendSms.otpmap = new HashMap<>();
        twilioSendSms.otpmap.put(phoneNumber, "654321");

        OtpBody otpBody = new OtpBody(phoneNumber, "123456");

        boolean isVerified = twilioSendSms.verification(otpBody);
        assertFalse(isVerified, "Verification should return false for an invalid OTP");
    }
}
