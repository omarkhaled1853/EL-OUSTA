package com.example.springsecurity.controller;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.entity.FetchUserRequest;
import com.example.springsecurity.entity.ResetPasswordRequest;
import com.example.springsecurity.entity.Technician;
import com.example.springsecurity.entity.UserInfo;
import com.example.springsecurity.service.JwtService;
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
    @Autowired
    private JwtService jwtService;


    @PostMapping("/user/fetchUser")
    public UserInfo fetchUser(@RequestBody FetchUserRequest request){
        return userInfoService.loadUserByUsernameAsUserInfo(request.getUsername());
    }

    @PostMapping("/tech/fetchTch")
    public Technician fetchTechnician(@RequestBody FetchUserRequest request){
        return technicianService.loadUserByUsernameAsTechnician(request.getUsername());
    }
    @PostMapping("/user/resetPassword")
    public String userResetPassword(@RequestBody ResetPasswordRequest request){
        String resetPasswordStatus = userInfoService.resetPassword(request);
        if (resetPasswordStatus.equals(ValidationStatus.VALID.getMessage())) {
            return jwtService.generateToken(request.getUsername());
        }
        return resetPasswordStatus; // fail (there is no user with the provided username)
    }
    @PostMapping("/tech/resetPassword")
    public String techResetPassword(@RequestBody ResetPasswordRequest request){
        String resetPasswordStatus = technicianService.resetPassword(request);
        if(resetPasswordStatus.equals(ValidationStatus.VALID.getMessage())){
            return jwtService.generateToken(request.getUsername());
        }
        return resetPasswordStatus; // fail (there is no user with the provided username)
    }
}
