package com.example.springsecurity.service;

import com.example.springsecurity.Modle.MailStructure;
import com.example.springsecurity.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailServiceTest {

    private MailService mailService;
    private JavaMailSender mockMailSender;

    @BeforeEach
    void setUp() throws Exception {
        // Create a real MailService instance
        mailService = new MailService();

        // Mock JavaMailSender
        mockMailSender = mock(JavaMailSender.class);

        // Use reflection to set the private mailSender field
        Field mailSenderField = MailService.class.getDeclaredField("mailSender");
        mailSenderField.setAccessible(true);
        mailSenderField.set(mailService, mockMailSender);
    }

    @Test
    void testGenerateOtp() {
        String otp = mailService.generateOtp();

        // Assert the OTP is a 6-digit number
        assertTrue(Pattern.matches("\\d{6}", otp), "OTP should be a 6-digit number");
    }

    @Test
    void testSendMail() {
        // Arrange
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Test Subject");
        String recipientEmail = "test@example.com";

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        mailService.sendMail(recipientEmail, mailStructure);

        // Assert
        verify(mockMailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage capturedMessage = messageCaptor.getValue();

        assertNotNull(capturedMessage);
        assertEquals("eloustaofficial@gmail.com", capturedMessage.getFrom(), "Sender email should match");
        assertEquals(recipientEmail, capturedMessage.getTo()[0], "Recipient email should match");
        assertEquals("Test Subject", capturedMessage.getSubject(), "Email subject should match");
        assertTrue(capturedMessage.getText().startsWith("the verification number is :"),
                "Email body should start with the OTP prefix");
    }
}
