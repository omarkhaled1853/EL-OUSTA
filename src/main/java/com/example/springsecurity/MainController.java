package com.example.springsecurity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
public class MainController {
   @GetMapping("/user/profile")
    public String getUserProfile(){
        return "welcome to user profile";
    }
    @GetMapping("/tech/profile")
    public String getTechProfile(){
        return "welcome to tech profile";
    }
}
