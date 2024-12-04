package com.example.springsecurity.controller;

import com.example.springsecurity.Modle.OtpBody;
import com.example.springsecurity.Modle.TwilioSmsRequest;
import com.example.springsecurity.service.TwilioSendSms;
import com.example.springsecurity.service.TwilioServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/twilio-otp")
public class TwilioController {

    TwilioServiceHandler twilioServiceHandler;
    @Autowired
    public TwilioController(TwilioServiceHandler twilioServiceHandler) {
        this.twilioServiceHandler = twilioServiceHandler;
    }
    @PostMapping("/twilio-sms")
    public String sendtwiliosms(@RequestBody TwilioSmsRequest twilioSmsRequest)
    {
        twilioServiceHandler.sendsms(twilioSmsRequest);
        return "send massage succesfully";
    }
//    editing                  //////////////////////////
    @PostMapping("/verification")
    public boolean verificationotp(@RequestBody OtpBody otpBody)
    {
        if(twilioServiceHandler.verifaying(otpBody)) {
            System.out.println("successfully verification");
            TwilioSendSms.otpmap.put(otpBody.getUserphonenumber(),null);
            return true;
        }
        else System.out.println("not correct otp try again");
        return  false;
    }

}
