package com.ELOUSTA.ELOUSTA.backend.controller.payment;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.model.PaymentRequest;
import com.ELOUSTA.ELOUSTA.backend.service.auth.ClientAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import com.ELOUSTA.ELOUSTA.backend.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/tech")
public class TechPaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-payment-intent")
    public Map<String, Object> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPaymentIntent(paymentRequest.getAmount(), paymentRequest.getCurrency());
    }
}