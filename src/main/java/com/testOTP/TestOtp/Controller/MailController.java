package com.testOTP.TestOtp.Controller;

import com.testOTP.TestOtp.Modle.MailStructure;
import com.testOTP.TestOtp.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    MailService mailService;
    @PostMapping("/send/{mail}")
    public String sendmail(@PathVariable String mail , @RequestBody MailStructure mailStructure)
    {
        mailService.sendMail(mail,mailStructure);
        return "Successfully send";
    }
}
