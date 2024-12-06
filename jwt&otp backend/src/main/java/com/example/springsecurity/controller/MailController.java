package com.example.springsecurity.controller;

import com.example.springsecurity.Modle.MailStructure;
import com.example.springsecurity.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RecursiveTask;

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
    @PostMapping("/verification/{mail}/{inputotp}")
    public Boolean verificationmaiotp(@PathVariable String mail , @PathVariable String inputotp)
    {
        if(mailService.verification(mail,inputotp)) {
            System.out.println("Successfully verified");
        }
        else System.out.println("not coorect please try again");
        return mailService.verification(mail,inputotp);
    }
}
