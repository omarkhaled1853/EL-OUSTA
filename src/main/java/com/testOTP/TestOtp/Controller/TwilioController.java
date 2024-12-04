package com.testOTP.TestOtp.Controller;

import com.testOTP.TestOtp.Modle.OtpBody;
import com.testOTP.TestOtp.Modle.TwilioSmsRequest;
import com.testOTP.TestOtp.Service.TwilioSendSms;
import com.testOTP.TestOtp.Service.TwilioServiceHandler;
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
