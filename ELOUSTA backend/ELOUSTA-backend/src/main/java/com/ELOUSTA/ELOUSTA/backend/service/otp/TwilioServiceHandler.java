package com.ELOUSTA.ELOUSTA.backend.service.otp;

import com.ELOUSTA.ELOUSTA.backend.model.OtpBody;
import com.ELOUSTA.ELOUSTA.backend.model.TwilioSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceHandler {
    private final TwilioSendSms twilioSendSms;
    @Autowired
    public TwilioServiceHandler(TwilioSendSms twilioSendSms) {
        this.twilioSendSms = twilioSendSms;
    }
    public void sendsms(TwilioSmsRequest twilioSmsRequest)
    {
        twilioSendSms.sendtwiliosms(twilioSmsRequest);
    }
    public boolean verifaying(OtpBody otpBody)
    {
        return twilioSendSms.verification(otpBody);
    }


}
