package com.testOTP.TestOtp.Controller;
import com.testOTP.TestOtp.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-sms/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> sendOtp(@PathVariable String phoneNumber) {
        Map<String, Object> response = new HashMap<>();

        // Generate OTP
        String otp = otpService.generateOtp();

        // Send OTP
        String sendResponse = otpService.sendOtp(phoneNumber, otp);

        if (sendResponse.contains("Failed")) {
            response.put("message", "Error sending OTP");
            response.put("error", sendResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Return response with OTP
        response.put("message", "OTP sent successfully.");
        response.put("otp", otp);
        return ResponseEntity.ok(response);
    }
}