package com.example.springsecurity.controller;

import com.example.springsecurity.entity.ResetPasswordRequest;
import com.example.springsecurity.entity.Technician;
import com.example.springsecurity.entity.UserInfo;
import com.example.springsecurity.service.TechnicianService;
import com.example.springsecurity.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class ForgetPasswordController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TechnicianService technicianService;

    @GetMapping("/user/fetchUser/{UserName}")
    public UserInfo fetchUser(@PathVariable String UserName){
        return userInfoService.loadUserByUsernameAsUserInfo(UserName);
    }

    @GetMapping("/tech/fetchTch/{UserName}")
    public Technician fetchTechnician(@PathVariable String UserName){
        return technicianService.loadUserByUsernameAsTechnician(UserName);
    }
    @PostMapping("/user/resetPassword")
    public String userResetPassword(@RequestBody ResetPasswordRequest request){
        return userInfoService.resetPassword(request);
    }
    @PostMapping("/tech/resetPassword")
    public String techResetPassword(@RequestBody ResetPasswordRequest request){
        return technicianService.resetPassword(request);
    }
}
