package com.testOTP.TestOtp.Service;

import com.testOTP.TestOtp.Modle.OtpBody;
import com.testOTP.TestOtp.Modle.TwilioSmsRequest;
import com.testOTP.TestOtp.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioSendSms {
    private final Logger LOGGER = LoggerFactory.getLogger(TwilioSendSms.class);
    private final TwilioConfiguration twilioConfiguration;

    public static Map<String,String> otpmap = new HashMap<String,String>();
    @Autowired
    public TwilioSendSms(TwilioConfiguration twilioConfiguration)
    {
        this.twilioConfiguration = twilioConfiguration;
    }
    public void sendtwiliosms(TwilioSmsRequest twilioSmsRequest){
    String otp = generateOtp();
    PhoneNumber to = new PhoneNumber(twilioSmsRequest.getPhonenuber());
    PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrial_number());
    String message = "the verification number is : " + otp ;
    MessageCreator creator = Message.creator(to, from, message);
    creator.create();
    otpmap.put(twilioSmsRequest.getPhonenuber(),otp);
    System.out.println(otpmap.toString());
    LOGGER.info("send sms to {}"+twilioSmsRequest);
    }

    public String generateOtp() {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    public boolean verification(OtpBody otpBody) {
        System.out.println(this.otpmap.get(otpBody.getUserphonenumber()));
        System.out.println(otpBody.getOtp());
        if(this.otpmap.get(otpBody.getUserphonenumber()).equals(otpBody.getOtp())) {
            return true;
        }
        return false;
    }
}
