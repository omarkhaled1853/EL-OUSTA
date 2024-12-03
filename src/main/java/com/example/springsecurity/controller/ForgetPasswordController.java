package com.example.springsecurity.controller;

import com.example.springsecurity.entity.FetchUserRequest;
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


    @PostMapping("/user/fetchUser")
    public UserInfo fetchUser(@RequestBody FetchUserRequest UserName){
        return userInfoService.loadUserByUsernameAsUserInfo(UserName.getUsername());
    }

    @PostMapping("/tech/fetchTch")
    public Technician fetchTechnician(@RequestBody FetchUserRequest UserName){
        return technicianService.loadUserByUsernameAsTechnician(UserName.getUsername());
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
