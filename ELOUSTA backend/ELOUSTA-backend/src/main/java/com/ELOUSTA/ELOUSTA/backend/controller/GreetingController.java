package com.ELOUSTA.ELOUSTA.backend.controller;

import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class GreetingController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/client/sendGreeting")
    public String sendGreeting(){
        String message = "hello new notification";

        try{
            notificationService.sendNotificationToClient(message, 1);
            return "Greeting send successfully";
        }catch (Exception e){
            return "Failed to send greeting: " + e.getMessage();
        }
    }
}